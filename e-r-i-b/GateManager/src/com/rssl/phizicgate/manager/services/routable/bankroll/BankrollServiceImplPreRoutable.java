package com.rssl.phizicgate.manager.services.routable.bankroll;

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
import com.rssl.phizicgate.manager.services.routable.RouteInfoService;

import java.util.Calendar;
import java.util.List;

/**
 * @ author: filimonova
 * @ created: 16.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * Поиск маршрутизирующей информации, если она отсутсвует в объекте маршрутизации. 
 */


public class BankrollServiceImplPreRoutable extends AbstractService implements BankrollService
{
	private static BankrollService routable;
	public static final String ROUTABLE_KEY = BankrollService.class.getName()+".routable";
	private RouteInfoService routeInfoService = new RouteInfoService();

	public BankrollServiceImplPreRoutable(GateFactory factory)
	{
		super(factory);
		try
		{
			String routableClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(ROUTABLE_KEY);
			Class routableService = ClassHelper.loadClass(routableClassName);
			routable = (BankrollService) routableService.getConstructor(GateFactory.class).newInstance(factory);
		}
		catch (Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		return routable.getClientAccounts(client);
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		try
		{
			accountIds = routeInfoService.appendRouteInfo(accountIds);
		}
		catch (GateException e)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, e);
		}
		catch (GateLogicException e)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, e);
		}
		return routable.getAccount(accountIds);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		return routable.getAccountByNumber(accountInfo);
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		return routable.getAbstract(routeInfoService.appendRouteInfo(object), fromDate,toDate,withCardUseInfo);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		try
		{
			object = routeInfoService.appendRouteInfo(object);
		}
		catch (GateException e)
		{
			GroupResultHelper.getOneErrorResult(object, e);
		}
		catch (GateLogicException e)
		{
			GroupResultHelper.getOneErrorResult(object, e);
		}
		return routable.getAbstract(number, object);
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
		try
		{
			card = routeInfoService.appendRouteInfo(card);
		}
		catch (GateException e)
		{
			GroupResultHelper.getOneErrorResult(card, e);
		}
		catch (GateLogicException e)
		{
			GroupResultHelper.getOneErrorResult(card, e);
		}
		return routable.getCardPrimaryAccount(card);
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
		try{
			accounts = routeInfoService.appendRouteInfo(accounts);
		}
		catch (GateException e)
		{
			GroupResultHelper.getOneErrorResult(accounts, e);
		}
		catch (GateLogicException e)
		{
			GroupResultHelper.getOneErrorResult(accounts, e);
		}
		return routable.getOwnerInfo(accounts);
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return routable.getAccountExtendedAbstract(routeInfoService.appendRouteInfo(account), fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		return routable.getAccHistoryFullExtract(routeInfoService.appendRouteInfo(account), fromDate, toDate, withCardUseInfo);
	}
}
