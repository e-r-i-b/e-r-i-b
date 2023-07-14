package com.rssl.phizicgate.sbrf.xbank;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractDataSourceServiceGate;
import com.rssl.phizic.gate.impl.DataSourceConfig;
import com.rssl.phizic.gate.messaging.GateMessagingClientException;
import com.rssl.phizic.gate.messaging.GateMessagingException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.gate.messaging.NonUniqueMessageIdException;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Gololobov
 * @ created 18.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceImpl extends AbstractDataSourceServiceGate implements BankrollService
{
	//отказ в исполнении документа по причине дублирования уникального идентификатора
	private static final String ERROR_UNIQUE_CODE   = "ERROR_UNIQUE";
	//отказ свидетельствует о некорректной работе одной из систем
	private static final String ERROR_VALIDATE_CODE = "ERROR_VALIDATE";
	//причина отказа может быть сообщена на рабочее место клиента
	private static final String ERROR_CLIENT_CODE = "ERROR_CLIENT";

	private static final String ERROR_XML_PARSE_MESSAGE = "Ошибка при разборе XML-ответа. ";

	protected BankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected String getDataSourceName()
	{
		return ConfigFactory.getConfig(DataSourceConfig.class).getDataSourceName();
	}

	@Override
	protected System getSystem()
	{
		return System.jdbc;
	}

	public Document getAccountAbstract(Object object, Calendar fromDate, Calendar toDate, boolean isExtended) throws GateException
	{
		try
		{
			return executeJDBCAction(new SBRFXBankAction((Account) object, fromDate, toDate, isExtended));
		}
		catch (SystemException e)
		{
			throw new  GateException(e);
		}
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		//BUG055254: Неверный запрос информации по карте/вкладу
		//ошибка появляется у СБОЛ клиентов, у которых внешний идентификатор счета соответсвует виду <id>|<routeInfo>
		//при попытке получения информации о счете в шлюзе xBank отвечаем так,
		//будто счет не найден, потому что если б он был в xBank,
		//то вернулся бы в GFL, и идентификатор обновился бы на шинный.
		GroupResult<String, Account> res = new GroupResult<String, Account>();
		for (String accountId : accountIds)
		{
			res.putException(accountId, new GateLogicException("Не найден счет с id = "+accountId));
		}
		return res;
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		throw new UnsupportedOperationException();
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Card> getCard(String... cardIds)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		throw new UnsupportedOperationException();
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if(object instanceof Account)
		{
			//Запрос на получение выписки
			Document xmlResponse = getAccountAbstract(object,fromDate,toDate,withCardUseInfo);

			//Заполнение выписки полученными сервисом данными
			AccountAbstractImpl accountAbstract = new AccountAbstractImpl();
			fillAbstract(xmlResponse, accountAbstract);
			return accountAbstract;
		}
		else
			throw new GateException("Неверный тип объекта");
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		if (object[0] instanceof Account)
			return getAccountAbstract(object);
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		//Запрос на получение выписки
		Document document =getAccountAbstract(account,fromDate,toDate,true);
		if (document == null)
		{
			throw new GateException("Сбой при получении выписки по счету: "+account.getNumber());
		}
		//Заполнение выписки полученными сервисом данными
		AccountAbstractImpl accountAbstract = new AccountAbstractImpl();
		fillAbstract(document, accountAbstract);

		Element response = document.getDocumentElement();

		String closedSum = XmlHelper.getSimpleElementValue(response, "ClosedSum");
		String closedDate = XmlHelper.getSimpleElementValue(response, "ClosedDate");
		String currencyCode = XmlHelper.getSimpleElementValue(response, "currencyCode");
		String additionalInformation = XmlHelper.getSimpleElementValue(response, "AdditionalInform");

		NodeList nodeList = null;
		try
		{
			Element proxyList = XmlHelper.selectSingleNode(response,"billing_a");
			if (proxyList != null)
				nodeList = XmlHelper.selectNodeList(proxyList, "Proxy");
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}

		CurrencyService currencyService = getFactory().service(CurrencyService.class);
		if(StringHelper.isNotEmpty(closedSum) && StringHelper.isNotEmpty(currencyCode))
		{
			Currency currency = currencyService.findByAlphabeticCode( currencyCode );
			if (currency != null)
				accountAbstract.setClosedSum(new Money( new BigDecimal(closedSum), currency));
		}

		if(StringHelper.isNotEmpty(closedDate))
		{
			accountAbstract.setClosedDate(XMLDatatypeHelper.parseDate(closedDate));
		}

		accountAbstract.setAdditionalInformation(additionalInformation);

		List<Trustee> trustees = new ArrayList<Trustee>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);

			String name = XmlHelper.getSimpleElementValue(element, "name");
			String date = XmlHelper.getSimpleElementValue(element, "date");

			TrusteeImpl trusteesDocument = new TrusteeImpl();
			if (!StringHelper.isEmpty(date))
				trusteesDocument.setEndingDate( XMLDatatypeHelper.parseDate(date) );
			else trusteesDocument.setEndingDate( null );
			trusteesDocument.setName( name );
			trustees.add( trusteesDocument );
		}

		accountAbstract.setTrusteesDocuments(trustees);

		return accountAbstract;
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	private GroupResult<Object, AbstractBase> getAccountAbstract(Object... objects)
	{
		GroupResult<Object, AbstractBase> res = new GroupResult<Object, AbstractBase>();
		for (Object object: objects)
		{
			try{
				AccountAbstract accountAbstract = getAccountExtendedAbstract((Account)object, DateHelper.getPreviousMonth(), Calendar.getInstance());
				res.putResult(object, accountAbstract);
			}catch (IKFLException e)
			{
				res.putException(object, e);
			}
		}
		return res;
	}

	/**
	 * Заполнение выписки данными из XML
	 * @param document - XML-документ с данными выписки
	 * @param accountAbstract
	 */
	private void fillAbstract(Document document, AccountAbstractImpl accountAbstract) throws GateException, GateLogicException
	{
		Element response = document.getDocumentElement();
		//Проверка на отказ в исполнении операции
		checkErrorMessage(response);
		//Разбор пришедшей информации
		NodeList nodeList = null;
		try
		{                                                                              
			Element transactionsList = XmlHelper.selectSingleNode(response,"billing_a");
			if (transactionsList != null)
				nodeList = XmlHelper.selectNodeList(transactionsList, "row");
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}
		String startDate = XmlHelper.getSimpleElementValue(response, "startDate");
		String endDate = XmlHelper.getSimpleElementValue(response, "endDate");
		String openingBalance = XmlHelper.getSimpleElementValue(response, "beginningBalance");
		String closingBalance = XmlHelper.getSimpleElementValue(response, "outBalance");
		String currencyCode = XmlHelper.getSimpleElementValue(response, "currencyCode");
		String previousOperationDate = XmlHelper.getSimpleElementValue(response, "prevOperDate");

		if (!StringHelper.isEmpty(startDate))
		{
			try {
				accountAbstract.setFromDate( XMLDatatypeHelper.parseDate(startDate) );
			}
			catch(IllegalArgumentException ex)
			{
				log.error(ERROR_XML_PARSE_MESSAGE+" Неверный формат даты начала периода выписки (startDate): "+startDate,ex);
			}
		};

		if (!StringHelper.isEmpty(endDate))
		{
			try {
				accountAbstract.setToDate( XMLDatatypeHelper.parseDate(endDate) );
			}
			catch(IllegalArgumentException ex)
			{
				log.error(ERROR_XML_PARSE_MESSAGE+" Неверный формат даты окончания периода выписки (endDate): "+endDate,ex);
			}
		};

		Currency currency = null;
		if (StringHelper.isNotEmpty(currencyCode))
		{
			CurrencyService currencyService = getFactory().service(CurrencyService.class);
			currency = currencyService.findByAlphabeticCode( currencyCode );
		}

		if (currency != null)
		{
			if (StringHelper.isNotEmpty(openingBalance))
				accountAbstract.setOpeningBalance( new Money(new BigDecimal(openingBalance),currency) );
			if(StringHelper.isNotEmpty(closingBalance))
				accountAbstract.setClosingBalance( new Money(new BigDecimal(closingBalance),currency) );
		}

		if(StringHelper.isNotEmpty(previousOperationDate))
		{
			try {
				accountAbstract.setPreviousOperationDate(XMLDatatypeHelper.parseDate(previousOperationDate));
			}
			catch(IllegalArgumentException ex)
			{
				log.error(ERROR_XML_PARSE_MESSAGE+" Неверный формат даты преыдущей операции (previousOperationDate): "+previousOperationDate,ex);
			}
		}

		List<TransactionBase> transactions = new ArrayList<TransactionBase>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Element element = (Element) nodeList.item(i);
			String balance = XmlHelper.getSimpleElementValue(element, "balance");
			String corAccount = XmlHelper.getSimpleElementValue(element, "correspondent");
			String isDepit = XmlHelper.getSimpleElementValue(element, "debit");
			String sum = XmlHelper.getSimpleElementValue(element, "sum");
			String date = XmlHelper.getSimpleElementValue(element, "date");
			String description = XmlHelper.getSimpleElementValue(element, "name");
			String documentNumber = XmlHelper.getSimpleElementValue(element, "document");
			String operatonType = XmlHelper.getSimpleElementValue(element, "aspect");

			AccountTransactionImpl transaction = new AccountTransactionImpl();
			if (currency != null)
			{
				if (StringHelper.isNotEmpty(balance))
					transaction.setBalance(new Money(new BigDecimal(balance),currency));
				transaction.setCounteragentAccount(corAccount);

				if (StringHelper.isNotEmpty(sum))
				{
					if(Boolean.parseBoolean(isDepit))
						transaction.setDebitSum( new Money(new BigDecimal(sum),currency) );
					else
						transaction.setCreditSum( new Money(new BigDecimal(sum),currency) );
				}
			}

			if (!StringHelper.isEmpty(date))
			{
				try {
					transaction.setDate( XMLDatatypeHelper.parseDate(date) );
				}
				catch(IllegalArgumentException ex)
				{
					log.error(ERROR_XML_PARSE_MESSAGE+" Неверный формат даты операции (date): "+date,ex);
				}
			}

			transaction.setDescription( description );
			transaction.setDocumentNumber(documentNumber);
			transaction.setOperationCode(operatonType);

			transactions.add( transaction );
		}
		accountAbstract.setTransactions( transactions );
	}

	/**
	 * Проверка на отказ в исполнении операции
	 * @param response
	 */
	private void checkErrorMessage(Element response) throws GateException, GateLogicException
	{
		try
		{   //Проверим, возможно пришел отказ в исполнении
			NodeList errorsList = XmlHelper.selectNodeList(response,"error_a");
			for (int i = 0; i < errorsList.getLength(); i++)
			{
				Element element = (Element) errorsList.item(i);
				String errorCode = XmlHelper.getSimpleElementValue(element, "code");
				String errorMessage = XmlHelper.getSimpleElementValue(element, "message");

				if(errorCode.equals(ERROR_UNIQUE_CODE))
				{
					throw new NonUniqueMessageIdException(errorMessage);
				}
				else if (errorCode.equals(ERROR_VALIDATE_CODE))
				{
					throw new GateMessagingValidationException(errorMessage);
				}
				else if (errorCode.equals(ERROR_CLIENT_CODE))
				{
					throw new GateMessagingClientException(errorMessage);
				}
				else
				{
					if(errorCode!=null && errorMessage!=null)
						throw new GateMessagingException(errorMessage, errorCode, errorMessage);
					else
						throw new GateMessagingException(errorMessage);
				}
			}
		}
		catch(TransformerException ex)
		{
			throw new GateException(ex);
		}
	}
}
