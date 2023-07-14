package com.rssl.phizicgate.manager.services.persistent.bankroll;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * @author Krenev
 * @ created 20.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceImpl extends PersistentServiceBase<BankrollService> implements BankrollService
{
	public BankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		List<Account> accounts = delegate.getClientAccounts(removeRouteInfo(client));
		List<Account> rezult = new ArrayList<Account>();
		for (Account account : accounts)
		{
			rezult.add(storeRouteInfo(account));
		}
		return rezult;
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		GroupResult<String, Account> accounts = delegate.getAccount(removeRouteInfo(accountIds));
		try{
			return storeRouteInfo(accounts);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, e);
		}
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		return delegate.getAbstract(removeRouteInfo(object), fromDate, toDate, withCardUseInfo);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		return delegate.getAbstract(number, removeRouteInfo(object));
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		List<Card> cards = delegate.getClientCards(removeRouteInfo(client));
		List<Card> rezult = new ArrayList<Card>();
		for (Card card : cards)
		{
			rezult.add(storeRouteInfo(card));
		}
		return rezult;
	}

	public GroupResult<String, Card> getCard(String... cardId)
	{
		GroupResult<String, Card> cards = delegate.getCard(removeRouteInfo(cardId));
		try{
			return storeRouteInfo(cards);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardId, e);
		}
	}

	public GroupResult<Card,List<Card>> getAdditionalCards(Card... mainCard)
	{
		GroupResult<Card,List<Card>> cards = delegate.getAdditionalCards(removeRouteInfo(mainCard));
		try{
			return storeRouteInfo(cards);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, e);
		}
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		GroupResult<Card, Account> accounts = delegate.getCardPrimaryAccount(removeRouteInfo(card));
		try{
			return storeRouteInfo(accounts);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(card, e);
		}
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		GroupResult<Card, Client> clients = delegate.getOwnerInfo(removeRouteInfo(card));
	    try
	    {
			return storeRouteInfo(clients);
	    }
	    catch (IKFLException e)
	    {
			return GroupResultHelper.getOneErrorResult(card, e);    
	    }
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		GroupResult<Pair<String, Office>, Client> clients = delegate.getOwnerInfoByCardNumber(cardInfo);

		try
	    {
			return storeRouteInfo(clients);
	    }
	    catch (IKFLException e)
	    {
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
	    }
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		GroupResult<Account, Client> clients = delegate.getOwnerInfo(removeRouteInfo(account));
	    try{
		    return storeRouteInfo(clients);
	    }
	    catch (IKFLException e)
	    {
			return GroupResultHelper.getOneErrorResult(account, e);
	    }
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		return delegate.getAccountExtendedAbstract(removeRouteInfo(account), fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		GroupResult<Pair<String, Office>, Account> accounts = delegate.getAccountByNumber(removeRouteInfo(accountInfo));
		try{
			return storeRouteInfo(accounts);
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, e);
		}
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		try{
			return storeRouteInfo(delegate.getCardByNumber(removeRouteInfo(client), removeRouteInfo(cardInfo)));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
		}
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		return delegate.getAccHistoryFullExtract(removeRouteInfo(account), fromDate, toDate, withCardUseInfo);
	}
}
