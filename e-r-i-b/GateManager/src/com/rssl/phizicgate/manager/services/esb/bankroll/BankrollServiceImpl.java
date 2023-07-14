package com.rssl.phizicgate.manager.services.esb.bankroll;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

import java.util.Calendar;
import java.util.List;

/**
 * @ author: filimonova
 * @ created: 23.09.2010
 * @ $Author$
 * @ $Revision$
 * Маршрутизация запросов BankrollService'a на шину в зависимости от:
 * 1. department.isESBSupported
 */
public class BankrollServiceImpl extends AbstractService implements BankrollService
{
	public static final String ESB_KEY = BankrollService.class.getName()+".esb";
	public static final String ROUTABLE_KEY = BankrollService.class.getName()+".preroutable";
	private static BankrollService esb;
	private static BankrollService preroutable;

	public BankrollServiceImpl(GateFactory factory)
	{
		super(factory);

		try
		{
			String esbClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(ESB_KEY);
			Class esbService = ClassHelper.loadClass(esbClassName);
			esb = (BankrollService) esbService.getConstructor(GateFactory.class).newInstance(factory);

			String routableClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(ROUTABLE_KEY);
			Class routableService = ClassHelper.loadClass(routableClassName);
			preroutable = (BankrollService) routableService.getConstructor(GateFactory.class).newInstance(factory);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		boolean esbSupported = client.getOffice() == null? ESBHelper.isESBSupported(client.getId()) :ESBHelper.isESBSupported(client.getOffice());		

		if (esbSupported)
			return esb.getClientAccounts(client);
		else
			return preroutable.getClientAccounts(client);
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		if (accountIds == null || accountIds.length == 0)
			return new GroupResult<String, Account>();
		// Информацию об офисе берем из первого параметра - считаем остальные такими же
		String accountId = accountIds[0];
		try{
		if (ESBHelper.isESBSupported(accountId))
				return esb.getAccount(accountIds);
			else
				return preroutable.getAccount(accountIds);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, e);
		}
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		if (accountInfo == null)
			return new GroupResult<Pair<String, Office>, Account>();

		try{
			if (ESBHelper.isESBSupported(accountInfo[0].getSecond()))
				return esb.getAccountByNumber(accountInfo);
			return preroutable.getAccountByNumber(accountInfo);
		}catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, e);
		}
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException("Получение расширенной выписки не поддерживается");
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		if (object[0] instanceof Account)
		{
			// Информацию об офисе берем из первого параметра - считаем остальные такими же
			try{
				if (ESBHelper.isESBSupported(((Account)object[0]).getId()))
					return esb.getAbstract(number, object);
				else
					return preroutable.getAbstract(number, object);
			}catch (GateException e)
			{
				return GroupResultHelper.getOneErrorResult(object, e);
			}
		}
		throw new UnsupportedOperationException("Получение расширенной выписки для " + object + " не поддерживается");
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

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... cards)
	{
		if(cards == null || cards.length == 0)
		{
			return new GroupResult<Card, Account>();
		}

		try
		{   
			if (ESBHelper.isESBSupported(cards[0].getId()))
			{
				return esb.getCardPrimaryAccount(cards);
			}
			else
			{
				return preroutable.getCardPrimaryAccount(cards);
			}
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(cards, e);
		}
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... accounts)
	{
		if (accounts == null || accounts.length == 0)
			return new GroupResult<Account, Client>();
		// Информацию об офисе берем из первого параметра - считаем остальные такими же
		Account account = accounts[0];

		try{
			if (ESBHelper.isESBSupported(account.getId()))
				return esb.getOwnerInfo(accounts);
			else
				return preroutable.getOwnerInfo(accounts);
		}catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(accounts, e);
		}
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		if (account == null)
			return null;

	    if(ESBHelper.isESBSupported(account.getId()))
	        return esb.getAccountExtendedAbstract(account, fromDate, toDate);
		else
			return preroutable.getAccountExtendedAbstract(account, fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
