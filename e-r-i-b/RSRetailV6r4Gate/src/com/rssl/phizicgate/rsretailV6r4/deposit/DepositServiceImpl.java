package com.rssl.phizicgate.rsretailV6r4.deposit;

import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizicgate.rsretailV6r4.data.RSRetailV6r4Executor;
import com.rssl.phizicgate.rsretailV6r4.bankroll.BankrollServiceImpl;
import com.rssl.phizicgate.rsretailV6r4.bankroll.AccountImpl;

import java.util.List;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.text.ParseException;

import org.hibernate.Session;
import org.hibernate.Query;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.transform.TransformerException;

public class DepositServiceImpl extends AbstractService implements DepositService
{
    public DepositServiceImpl(GateFactory factory)
    {
        super(factory);
    }

    public List<? extends Deposit> getClientDeposits(final Client client) throws GateException
    {
		try
		{
		    List<DepositImpl> list = RSRetailV6r4Executor.getInstance().execute(new HibernateAction<List<DepositImpl>>()
		    {
		        public List<DepositImpl> run(Session session) throws Exception
		        {
			        Query namedQuery = session.getNamedQuery("GetClientDeposits")
					        .setParameter("clientId", Long.parseLong(client.getId()));

			        return (List<DepositImpl>) namedQuery.list();
		        }
		    });
			for (DepositImpl deposit : list)
				fillDepositFromXml(deposit);
			return list;
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
    }

    public Deposit getDepositById(final String depositId) throws GateException
    {
		DepositImpl deposit = new DepositImpl();
	    deposit.setId(depositId);
		fillDepositFromXml(deposit);
		return deposit;
    }

	public DepositInfo getDepositInfo(final Deposit deposit) throws GateException
	{
		try
		{
		    DepositInfoImpl depositInfo = new DepositInfoImpl();
			BankrollServiceImpl bankrollService = new BankrollServiceImpl(GateSingleton.getFactory());
			String accNumber = GroupResultHelper.getOneResult(bankrollService.getAccount(deposit.getId())).getNumber();
			fillDepositInfoFromXml(depositInfo, accNumber);
			return depositInfo;
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	private void fillDepositFromXml(Deposit deposit_) throws GateException
	{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage request = service.createRequest("getClientDepositInfo_q");

		request.addParameter("date", String.format("%1$td.%1$tm.%1$tY", Calendar.getInstance().getTime()));

		Element depositElement = XmlHelper.appendSimpleElement(request.getDocument().getDocumentElement(), "deposit");
		BankrollServiceImpl bankrollService = new BankrollServiceImpl(GateSingleton.getFactory());
		try
		{
			String accNumber = GroupResultHelper.getOneResult(bankrollService.getAccount(deposit_.getId())).getNumber();
			XmlHelper.appendSimpleElement(depositElement, "accountNumber",accNumber);
		}
		catch (IKFLException e)
		{
			throw new GateException(e);
		}

		Document doc = null;
		try
		{
			doc = service.sendOnlineMessage(request, null);

			Element paramElem = doc.getDocumentElement();
			Currency curr = null;
			DepositImpl deposit = (DepositImpl)deposit_;
			CurrencyService currencyService = getFactory().service(CurrencyService.class);

			String currencyXml = getParam(paramElem, "currency");
			if (currencyXml != null)
				curr = currencyService.findByAlphabeticCode(currencyXml.equals("RUR") ? "RUB" : currencyXml);

			String sumXml = getParam(paramElem, "sum");
			if ((curr != null) && (sumXml != null))
			{
			   Money summa = new Money( new BigDecimal(sumXml.replace(',','.')), curr);
			   deposit.setAmount(summa);
			}

			String accountTypeIdXml = getParam(paramElem, "accountTypeId");
			if (accountTypeIdXml != null)
			{
			   deposit.setDescription(accountTypeIdXml);
			}

			String periodXml = getParam(paramElem, "period");
			if (periodXml != null)
			   deposit.setDuration(Long.decode(periodXml));

			String interestRateXml = getParam(paramElem, "interestRate");
			if (interestRateXml != null)
			{
				deposit.setInterestRate(new BigDecimal(interestRateXml.trim()));
			}

			String statusXml = getParam(paramElem, "status");
			if ("O".equals(statusXml))//O-большая-английская
				deposit.setState(DepositState.open);
			else if ("C".equals(statusXml))//C-большая-английская
				deposit.setState(DepositState.closed);
			else
				throw new GateException("Неизвестный статус депозита");

			String openDateXml = getParam(paramElem, "openDate");
			if (openDateXml != null)
			{
				deposit.setOpenDate(DateHelper.parseCalendar(openDateXml));
			}

			String endDateXml = getParam(paramElem, "endDate");
			if (endDateXml != null)
			{
				deposit.setEndDate(DateHelper.parseCalendar(endDateXml));
			}

			String closeDateXml = getParam(paramElem, "closeDate");
			if (closeDateXml != null)
			{
				deposit.setCloseDate(DateHelper.parseCalendar(closeDateXml));
			}
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
		catch (ParseException e)
		{
			throw new GateException(e);
		}
	}

	private void fillDepositInfoFromXml(DepositInfo depositInfo_, String accountNumber) throws GateException, TransformerException
	{
		BankrollServiceImpl bankrollService = new BankrollServiceImpl(GateSingleton.getFactory());
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage request = service.createRequest("getClientDepositInfo_q");
		request.addParameter("date", String.format("%1$td.%1$tm.%1$tY", Calendar.getInstance().getTime()));
		Element depositElement = XmlHelper.appendSimpleElement(request.getDocument().getDocumentElement(), "deposit");
		XmlHelper.appendSimpleElement(depositElement, "accountNumber",accountNumber);

		try
		{
			Document doc = service.sendOnlineMessage(request, null);

			Element paramElem = doc.getDocumentElement();
			Currency curr = null;
			DepositInfoImpl depositInfo = (DepositInfoImpl)depositInfo_;
			CurrencyService currencyService = getFactory().service(CurrencyService.class);

			String currencyXml = getParam(paramElem, "currency");
			if (currencyXml != null)
				curr = currencyService.findByAlphabeticCode(currencyXml.equals("RUR") ? "RUB" : currencyXml);

			String minimumAmountXml = getParam(paramElem, "minimumAmount");
			if ((curr != null) && (minimumAmountXml != null))
			{
			   Money summa = new Money( new BigDecimal(minimumAmountXml.replace(',','.')), curr);
			   depositInfo.setMinBalance(summa);
			}

			String numberXml = getParam(paramElem, "number");
			if (numberXml != null)
			   depositInfo.setAgreementNumber(numberXml);

			String renewalXml = getParam(paramElem, "renewal");
			if ("true".equalsIgnoreCase(renewalXml))
				depositInfo.setRenewalAllowed(true);
			else
				depositInfo.setRenewalAllowed(false);

			String anticipatoryRemovalXml = getParam(paramElem, "anticipatoryRemoval");
			if ("true".equalsIgnoreCase(anticipatoryRemovalXml))
				depositInfo.setAnticipatoryRemoval(true);
			else
				depositInfo.setAnticipatoryRemoval(false);

			String additionalFeeXml = getParam(paramElem, "additionalFee");
			if ("true".equalsIgnoreCase(additionalFeeXml))
				depositInfo.setAdditionalFee(true);
			else
				depositInfo.setAdditionalFee(false);

			String minAdditionalFeeXml = getParam(paramElem, "minAdditionalFee");
			if ((curr != null) && (minAdditionalFeeXml != null))
			{
			   Money summa = new Money( new BigDecimal(minAdditionalFeeXml.replace(',','.')), curr);
			   depositInfo.setMinReplenishmentAmount(summa);
			}

			String percentAccountXml = getParam(paramElem, "percentAccount");
			if (percentAccountXml != null)
			{
				AccountImpl account = bankrollService.getAccountByNumber(percentAccountXml);
				depositInfo.setPercentAccount(account);
			}

			{

				NodeList list = paramElem.getElementsByTagName("finalAccount");
				int len = list.getLength();
				Map<Account, BigDecimal> map = new HashMap<Account, BigDecimal>();
				for(int i=0;i<len;++i)
				{
					Element document = (Element)list.item(i);
					String number = XmlHelper.selectSingleNode(document, "number").getTextContent();

					String part = XmlHelper.selectSingleNode(document, "part").getTextContent();
					AccountImpl account = bankrollService.getAccountByNumber(number);
					map.put(account, new BigDecimal(part));
				}
				depositInfo.setFinalAccounts(map);
			}

			String accountNumberXml = getParam(paramElem, "accountNumber");
			if (accountNumberXml != null)
			{
				AccountImpl account = bankrollService.getAccountByNumber(accountNumberXml);
				depositInfo.setAccount(account);
			}
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	private String getParam (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);

		if (list.getLength() > 0)
		{
			String valueElem = list.item(0).getTextContent();
			if (!valueElem.equals(""))
			   return valueElem;
			else
			   return null;
		}
		return null;
	}
}