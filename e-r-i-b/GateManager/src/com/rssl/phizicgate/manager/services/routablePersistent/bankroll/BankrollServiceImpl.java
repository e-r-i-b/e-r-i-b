package com.rssl.phizicgate.manager.services.routablePersistent.bankroll;

import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.objects.ClientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.objects.AccountWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.CardWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;
import com.rssl.phizicgate.manager.services.routablePersistent.StringRouteInfo;
import com.rssl.phizicgate.manager.services.routablePersistent.PairRouteInfo;

import java.util.*;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceImpl extends RoutablePersistentServiceBase<BankrollService> implements BankrollService
{
	/**
	 * создает сервис.
	 *
	 * @param factory фабрика.
	 */
	public BankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected BankrollService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(BankrollService.class);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		List<Account> accounts = endService(clientInner.getRouteInfo()).getClientAccounts(clientInner);
		List<Account> rezult = new ArrayList<Account>();
		for (Account account : accounts)
		{
			rezult.add(storeRouteInfo(account, clientInner.getRouteInfo()));
		}

		return rezult;
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		try
		{
			Map<String, List<StringRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(StringRouteInfo.class, accountIds);
			GroupResult<String, Account> accounts = new GroupResult<String, Account>();
			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked
				accounts.add(storeRouteInfo(endService(ri).getAccount(StringRouteInfo.toArray(routeInfoToIds.get(ri))), ri));
			}

			return accounts;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, e);
		}
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		RouteInfoReturner withoutRouteInfo = removeRouteInfo(object);
		String ri = withoutRouteInfo.getRouteInfo();
		return (AbstractBase) storeRouteInfo(endService(ri).getAbstract(withoutRouteInfo, fromDate, toDate, withCardUseInfo), ri);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		try
		{
			Map<String, List<RouteInfoReturner>> routeInfoToObjects = objectIdWithoutRouteInfoToRouteInfo(RouteInfoReturner.class, object);
			GroupResult<Object, AbstractBase> abstracts = new GroupResult<Object, AbstractBase>();
			for (String ri : routeInfoToObjects.keySet())
			{
				//noinspection unchecked,ToArrayCallWithZeroLengthArrayArgument
				abstracts.add(storeRouteInfo(endService(ri).getAbstract(number, routeInfoToObjects.get(ri).toArray(new Object[0])), ri));
			}

			return abstracts;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(object, e);
		}
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		ClientWithoutRouteInfo clientInner = removeRouteInfo(client);
		String ri = clientInner.getRouteInfo();
		List<Card> cards = endService(ri).getClientCards(clientInner);
		List<Card> rezult = new ArrayList<Card>();
		for (Card card : cards)
		{
			rezult.add(storeRouteInfo(card, ri));
		}
		return rezult;
	}

	public GroupResult<String, Card> getCard(String... cardId)
	{
		try
		{
			Map<String, List<StringRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(StringRouteInfo.class, cardId);
			GroupResult<String, Card> cards = new GroupResult<String, Card>();
			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked
				cards.add(storeRouteInfo(endService(ri).getCard(StringRouteInfo.toArray(routeInfoToIds.get(ri))), ri));
			}
			return cards;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardId, e);
		}
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		try
		{
			Map<String, List<CardWithoutRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(CardWithoutRouteInfo.class, mainCard);
			GroupResult<Card, List<Card>> cards = new GroupResult<Card, List<Card>>();

			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked,ToArrayCallWithZeroLengthArrayArgument
				cards.add(storeRouteInfo(endService(ri).getAdditionalCards(routeInfoToIds.get(ri).toArray(new CardWithoutRouteInfo[0])), ri));
			}

			return cards;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, e);
		}
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		try
		{
			Map<String, List<CardWithoutRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(CardWithoutRouteInfo.class, card);
			GroupResult<Card, Account> accounts = new GroupResult<Card, Account>();
			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked,ToArrayCallWithZeroLengthArrayArgument
				accounts.add(storeRouteInfo(endService(ri).getCardPrimaryAccount(routeInfoToIds.get(ri).toArray(new CardWithoutRouteInfo[0])), ri));
			}
			return accounts;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(card, e);
		}
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		try
		{
			Map<String, List<CardWithoutRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(CardWithoutRouteInfo.class, card);
			GroupResult<Card, Client> clients = new GroupResult<Card, Client>();
			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked,ToArrayCallWithZeroLengthArrayArgument
				clients.add(storeRouteInfo(endService(ri).getOwnerInfo(routeInfoToIds.get(ri).toArray(new CardWithoutRouteInfo[0])), ri));
			}
			return clients;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(card, e);
		}
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		try
		{
			Map<String, List<PairRouteInfo>> ri = objectIdWithoutRouteInfoToRouteInfo(PairRouteInfo.class, cardInfo);
			GroupResult<Pair<String, Office>, Client> clients = new GroupResult<Pair<String, Office>, Client>();
			for (String key : ri.keySet())
			{
				//noinspection unchecked
				clients.add(storeRouteInfo(endService(key).getOwnerInfoByCardNumber(PairRouteInfo.copyToArray(ri.get(key))), key));
			}
			return clients;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
		}
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		try
		{
			Map<String, List<AccountWithoutRouteInfo>> routeInfoToIds = objectIdWithoutRouteInfoToRouteInfo(AccountWithoutRouteInfo.class, account);
			GroupResult<Account, Client> clients = new GroupResult<Account, Client>();
			for (String ri : routeInfoToIds.keySet())
			{
				//noinspection unchecked,ToArrayCallWithZeroLengthArrayArgument
				clients.add(storeRouteInfo(endService(ri).getOwnerInfo(routeInfoToIds.get(ri).toArray(new AccountWithoutRouteInfo[0])), ri));
			}
			return clients;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(account, e);
		}
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		AccountWithoutRouteInfo accountInner = removeRouteInfo(account);
		String ri = accountInner.getRouteInfo();
		return (AccountAbstract) storeRouteInfo(endService(ri).getAccountExtendedAbstract(accountInner, fromDate, toDate), ri);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		try
		{
			Map<String, List<PairRouteInfo>> ri = objectIdWithoutRouteInfoToRouteInfo(PairRouteInfo.class, accountInfo);
			GroupResult<Pair<String, Office>, Account> accounts = new GroupResult<Pair<String, Office>, Account>();
			for (String key : ri.keySet())
			{
				//noinspection unchecked
				accounts.add(storeRouteInfo(endService(key).getAccountByNumber(PairRouteInfo.copyToArray(ri.get(key))), key));
			}
			return accounts;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, e);
		}
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		try
		{
			Map<String, List<PairRouteInfo>> ri = objectIdWithoutRouteInfoToRouteInfo(PairRouteInfo.class, cardInfo);
			GroupResult<Pair<String, Office>, Card> cards = new GroupResult<Pair<String, Office>, Card>();
			for (String key : ri.keySet())
			{
				//noinspection unchecked
				cards.add(storeRouteInfo(endService(key).getCardByNumber(removeRouteInfo(client), PairRouteInfo.copyToArray(ri.get(key))), key));
			}
			return cards;
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
		}
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate,  Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		AccountWithoutRouteInfo accountInner = removeRouteInfo(account);
		String ri = accountInner.getRouteInfo();
		return (AccountAbstract) storeRouteInfo(endService(ri).getAccHistoryFullExtract(accountInner, fromDate, toDate, withCardUseInfo), ri);
	}
}
