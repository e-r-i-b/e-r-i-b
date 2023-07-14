package com.rssl.phizic.operations.account;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.ListsURIResolver;
import com.rssl.phizic.business.resources.external.AccountFilter;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.UnknownAccountCurrencyException;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import org.w3c.dom.Document;

import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * User: Omeliyanchuk
 * Date: 07.08.2006
 * Time: 9:18:12
 */
public class ShowLongOfferOperation extends OperationBase<AccountFilter>
{
	private Account account = null;
	private Calendar startDate = null;
	private Calendar endDate = null;
	private String type = null;
	private String xslPath = null;
	String[] accounts = null;

	private static BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDateString(String startDate) throws ParseException
	{
		this.startDate = DateHelper.parseCalendar(startDate);
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDateString(String endDate) throws ParseException
	{
		this.endDate = DateHelper.parseCalendar(endDate);
	}

	public String getOfferType()
	{
		return type;
	}

	public void setOfferType(String type) throws BusinessException
	{
		this.type = type;
		this.xslPath = getXSLPath(type);
		if(xslPath == null)
			throw new BusinessException("Неизвестный тип длительного поручения");
	}

	public String[] getAccountList()
	{
		return accounts;
	}

	public void setAccountList(String[] list)
	{
		accounts = list;
	}

	/**
	 * Получение отчетов о длительном поручении
	 *
	 * @throws BusinessException
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	@Transactional
	public List<String> buildLongOfferHTMLs() throws BusinessException,BusinessLogicException, GateLogicException, GateException
	{
		List<String> result = new ArrayList<String>();

		checkDates();

		if(accounts == null)
			return result;

		if (accounts.length != 0)
		{
			for (String accountsString : accounts)
			{
				AccountLink acc = GetNextAccount(accountsString);

				if (acc != null)
				{
					Document accountXML = sendAccountRequest(acc);
					DOMSource srcXml;   // Для ЦОДа
					srcXml = new DOMSource(accountXML);
					result.add( makeHtml(acc, srcXml) );
				}
				else
				{
					throw new BusinessException("Неизвестный формат передачи счетов");
				}
			}
			return result;
		}
		return result;

	}
	//запрос отчета о длительных поручениях для счета
	private Document sendAccountRequest(AccountLink account) throws BusinessException, GateLogicException
	{
		if( (account == null) || (startDate == null) || (endDate == null) || (type == null))
			throw new BusinessException("Не установлены параметры операции");

		try
		{
			WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
			GateMessage requestCOD = serviceFacade.createRequest("form190ResultDemand_q");
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			requestCOD.addParameter("clientId", personData.getPerson().getClientId());
			requestCOD.addParameter("account", account.getNumber());
			requestCOD.addParameter("startDate", DateHelper.toXMLDateFormat(startDate.getTime()));
			requestCOD.addParameter("endDate", DateHelper.toXMLDateFormat(endDate.getTime()));

			return serviceFacade.sendOnlineMessage(requestCOD, null);// счета из ЦОДа
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	private AccountLink GetNextAccount(String accountStr) throws BusinessException,
			GateException, UnknownAccountCurrencyException, BusinessLogicException
	{
		int fst = accountStr.indexOf(':');
		if (fst != -1)
		{
			int scd = accountStr.indexOf('&');
			if (scd == -1) scd = accountStr.length();
			String num = accountStr.substring(fst + 1, scd);
			String accountNumber = GetSelectedAccountNumber(num);
			return PersonContext.getPersonDataProvider().getPersonData().findAccount(accountNumber);
		}
		return null;
	}
	//получение номера счета по id ресурса
	private String GetSelectedAccountNumber(String id) throws BusinessException, BusinessLogicException
	{
		Long lid = new Long(id);
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		AccountLink link = personData.getAccount(lid);

		if (link != null)
		{
			Account a = link.getValue();
			return a.getNumber();
		}
		else return null;

	}
	//построение HTML отчета для одного счета
	private String makeHtml(AccountLink account, DOMSource responce) throws BusinessException,BusinessLogicException
	{
		Office office = account.getOffice();

		final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
		Source srcXsl = new StreamSource(contextClassLoader.getResourceAsStream(xslPath));

		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setURIResolver(new ListsURIResolver());
		Transformer transformer;
		try
		{
			transformer = factory.newTransformer(srcXsl);
		}
		catch (TransformerConfigurationException ex)
		{
			throw new BusinessException("Неверная конфигурация xml-инфраструктуры.", ex);
		}

		if (account != null)
		{
			transformer.setParameter("subDivisionNum", office.getCode().getFields().get("branch"));
			transformer.setParameter("currencyIn", account.getAccount().getCurrency().getCode());
            transformer.setParameter("subDivisionName", office.getName());
		}

        transformer.setParameter("sumOutput", "");
		transformer.setParameter("reciverINN", "");

		StringWriter stringWriter = new StringWriter();
		try
		{
			transformer.transform(responce, new StreamResult(stringWriter));
		}
		catch (TransformerException ex)
		{
			throw new BusinessException("Получен ответ неизвестного формата", ex);
		}
		return stringWriter.toString();
	}

	private void checkDates() throws BusinessException, GateLogicException
	{
		if( (endDate == null) || (startDate == null))
			throw new BusinessException("Не установлены параметры операции");

		PeriodValidator validator = new PeriodValidator();
		String message = validator.validateDate(startDate, endDate, 6);

		if(message != null)
			throw new GateLogicException(message);
	}

	//определение типа отчета по параметру запроса, формирование отчета из xml
	private String getXSLPath(String type)
	{
		if (type != null)
		{
			if (type.equals("type1"))
				return "transferPhizLO.xsl";
			if (type.equals("type2"))
				return "convertCurrencyLO.xsl";
			if (type.equals("type3"))
				return "transferJurLO.xsl";
		}
		return null;
	}

	//класс для проверки на превышение срока длительности
	private class PeriodValidator
	{
		public Boolean checkPeriod(Calendar startDate, int periodMonth)
		{
			Date date = startDate.getTime();
			Date testDate = DateHelper.add(date, 0, periodMonth, 0);
			Date curDate = new Date();

			if (!(curDate.before(testDate) || (!curDate.before(testDate) && !curDate.after(testDate))))
				return false;
			else return true;
		}

		public String validateDate(Calendar startDate, Calendar endDate, int periodMonth)
		{
			if ((startDate != null) && (endDate != null))
			{
				if (!checkPeriod(startDate, periodMonth))
					return "Получение отчета о длительных поручениях возможно на срок не позднее "+periodMonth+" последних месяцев";
			}
			else
			{
				return "Задайте период отчета";
			}
			return null;
		}
	}
}
