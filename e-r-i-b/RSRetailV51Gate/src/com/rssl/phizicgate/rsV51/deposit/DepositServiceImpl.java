package com.rssl.phizicgate.rsV51.deposit;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.DepositState;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizicgate.rsV51.data.GateRSV51Executor;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.ParseException;

/**
 * @author Evgrafov
 * @ created 19.05.2006
 * @ $Author: khudyakov $
 * @ $Revision: 33986 $
 */

public class DepositServiceImpl extends AbstractService implements DepositService
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

    public DepositServiceImpl(GateFactory factory)
    {
        super(factory);
    }

	private Query createReadonlyQuery(Session session, String queryName)
	{
		Query query = session.getNamedQuery(queryName);
		query.setReadOnly(true).setFlushMode(FlushMode.NEVER);

		return query;
	}
	
    private List<Deposit> getDepositsList(final String clientId) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<List<Deposit>>()
            {
                public List<Deposit> run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetClientDeposits")
                            .setParameter("clientId", Long.decode(clientId));

                    List<Deposit> deposits = query.list();
                    return (deposits != null  ?  deposits  :  new ArrayList<Deposit>());
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	private DepositImpl getDeposit(final String depositId) throws GateException
    {
        try
        {
            return GateRSV51Executor.getInstance().execute(new HibernateAction<DepositImpl>()
            {
                public DepositImpl run(Session session) throws Exception
                {
                    Query query = createReadonlyQuery(session, "GetDepositInfo")
                            .setParameter("depositId", Long.decode(depositId));

                    return (DepositImpl)query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
    }

	private Document getDepositInfo (List<Deposit> depositList, String clientId) throws GateException
	{
		Document document = null;

        WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = service.createRequest("getClientDepositInfo_q");
		message.addParameter("clientId", clientId);
		message.addParameter("date", String.format("%1$te.%1$tm.%1$tY", DateHelper.getCurrentDate().getTime()));
		Document doc = message.getDocument();

	    for (Deposit dep : depositList)
	    {
	       Element deposit = XmlHelper.appendSimpleElement(doc.getDocumentElement(), "deposit");
		   XmlHelper.appendSimpleElement(deposit, "referenc", dep.getId());
	    }

	    try
	    {
		   document = service.sendOnlineMessage(doc, null);
	    }
		catch (GateLogicException e)
	    {
		    throw new GateException(e.getMessage());
	    }

		return document;
	}

	private String getParam (Element element, String name)
	{
		NodeList list = element.getElementsByTagName(name);
		String valueElem = "";

		if (list.getLength() > 0)
		{
			valueElem = list.item(0).getTextContent();
			if (!valueElem.equals(""))
			   return valueElem;
			else
			   return null;
		}

		return null;
	}

	private void setDepositValue (List<Deposit> depositList, Document document) throws GateException, GateLogicException
	{
		if ((depositList == null) ||(depositList.size() == 0) || (document == null))
		   return;

		NodeList list = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < list.getLength(); i++)
		{
			Element paramElem = (Element) list.item(i);
			String id = paramElem.getElementsByTagName("referenc").item(0).getTextContent();
			for (Deposit dep : depositList)
		       if (dep.getId().equals(id))
		       {
			       fillDepositFromXML(paramElem, dep);
			       break;
		       }
		}
	}

    public List<? extends Deposit> getClientDeposits(Client client) throws GateException, GateLogicException
    {
	    List<Deposit> depositList = getDepositsList(client.getId());
		// а название депозита откуда брать?

	    if ((depositList != null) && (depositList.size() > 0))
	    {
	       Document document = getDepositInfo(depositList, client.getId());
	       setDepositValue (depositList, document);
        }

	    return depositList;
    }

    public Deposit getDepositById(String depositId) throws GateException, GateLogicException
    {
	    DepositImpl deposit = getDeposit(depositId);

	    List<Deposit> depositList = new ArrayList<Deposit>();
	    depositList.add(deposit);

	    Document document = getDepositInfo(depositList, "");
	    if (document != null)
	    {
//здесь разбираем ответ
		    NodeList list = document.getDocumentElement().getChildNodes();
			Element paramElem = (Element) list.item(0);

		    fillDepositFromXML(paramElem, deposit);
	    }

        return depositList.get(0);
    }

	private void fillDepositFromXML(Element paramElem, Deposit depositIn) throws GateException, GateLogicException
	{
		Currency curr = null;
		Money summa = null;
		DepositImpl deposit = (DepositImpl)depositIn;
		CurrencyService currencyService = getFactory().service(CurrencyService.class);

		String valueParam = getParam(paramElem, "currency");
		if (valueParam != null)
			curr = currencyService.findById(valueParam);

		valueParam = getParam(paramElem, "sum");
		if ((curr != null) && (valueParam != null))
		{
		   summa = new Money( new BigDecimal(valueParam), curr);
		   deposit.setAmount(summa);
		}

		valueParam = getParam(paramElem, "period");
		if (valueParam != null)
		   deposit.setDuration(Long.decode(valueParam));

		valueParam = getParam(paramElem, "interestRate");
		if (valueParam != null)
		{
			if("noPercentRate".equals(valueParam))
			{
				/*
				если у вида вклада в заданном подразделении и валюте не указана процентная ставка -
				то делаем вид, что она равна 0, и СООБЩАЕМ ОБ ЭТОМ В ЛОГ
				*/
				deposit.setInterestRate(new BigDecimal(0));
				log.info("Не задана процентная ставка для счета с референсом" + getParam(paramElem,"referenc"));
			}
			else
				deposit.setInterestRate(new BigDecimal(valueParam.trim()));
		}

		valueParam = getParam(paramElem, "status");
		if ("O".equals(valueParam))
			deposit.setState(DepositState.open);
		else if ("C".equals(valueParam))
			deposit.setState(DepositState.closed);
		else
			throw new GateException("Неизвестный статус депозита");

		valueParam = getParam(paramElem, "endDateDep");
		if (valueParam != null)
		{
			try
			{
				deposit.setEndDate(DateHelper.parseCalendar(valueParam));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		valueParam = getParam(paramElem, "startDateDep");
		if (valueParam != null)
		{
			try
			{
				deposit.setOpenDate(DateHelper.parseCalendar(valueParam));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}

		valueParam = getParam(paramElem, "closeDate");
		if (valueParam != null)
		{
			try
			{
				deposit.setCloseDate(DateHelper.parseCalendar(valueParam));
			}
			catch (ParseException e)
			{
				throw new GateException(e);
			}
		}
	}

	public DepositInfo getDepositInfo(Deposit deposit) throws GateException, GateLogicException
	{
		List<Deposit> depositList = new ArrayList<Deposit>();
		depositList.add(deposit);

		Document document = getDepositInfo(depositList, "");
		if (document != null)
		{
//здесь разбираем ответ
			NodeList list = document.getDocumentElement().getChildNodes();
			Element paramElem = (Element) list.item(0);
			DepositInfoImpl depInfo = new DepositInfoImpl();
			fillDepositInfoFromXML(paramElem, depInfo);
			return depInfo;
		}
		else
			throw new GateException("Ошибка при получении расширенной информации о депозите");
	}

	private void fillDepositInfoFromXML(Element paramElem, DepositInfo depositInfoIn) throws GateException, GateLogicException
	{
		Currency curr = null;
		DepositInfoImpl depositInfo = (DepositInfoImpl) depositInfoIn;
		CurrencyService currencyService = getFactory().service(CurrencyService.class);
		BankrollService bankrollService = getFactory().service(BankrollService.class);

		String valueParam = getParam(paramElem, "number");
		if (valueParam != null)
		   depositInfo.setAgreementNumber(valueParam);

		valueParam = getParam(paramElem, "currency");
		if (valueParam != null)
			curr = currencyService.findById(valueParam);

		valueParam = getParam(paramElem, "minAdditionalFee");
		if ((curr != null) && (valueParam != null))
		{
		   Money summa = new Money( new BigDecimal(valueParam), curr);
		   depositInfo.setMinReplenishmentAmount(summa);
		}

		valueParam = getParam(paramElem, "minimumAmount");
		if ((curr != null) && (valueParam != null))
		{
		   Money summa = new Money( new BigDecimal(valueParam), curr);
		   depositInfo.setMinBalance(summa);
		}

		valueParam = getParam(paramElem, "renewal");
		depositInfo.setRenewalAllowed("1".equals(valueParam));

		valueParam = getParam(paramElem, "anticipatoryRemoval");
		depositInfo.setAnticipatoryRemoval("1".equals(valueParam));

		valueParam = getParam(paramElem, "additionalFee");
		depositInfo.setAdditionalFee("1".equals(valueParam));

		try
		{
			valueParam = getParam(paramElem, "referenc");
			if (valueParam != null)
				depositInfo.setAccount(GroupResultHelper.getOneResult(bankrollService.getAccount(valueParam)));

			valueParam = getParam(paramElem, "accountRef");
			Map<Account, BigDecimal> map = new HashMap<Account, BigDecimal>();
			if (valueParam != null)
			{
				AccountImpl accountImpl = new AccountImpl();
				accountImpl.setNumber(valueParam);
				map.put(GroupResultHelper.getOneResult(bankrollService.getAccount(valueParam)),new BigDecimal(100));
			}
			depositInfo.setFinalAccounts(map);

			valueParam = getParam(paramElem, "account");
			if (valueParam != null)
				depositInfo.setPercentAccount(GroupResultHelper.getOneResult(bankrollService.getAccount(valueParam)));
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e);
		}
		catch (SystemException e)
		{
			throw new GateException("Ошибка при получении расширенной информации о депозите", e);
		}
	}
}