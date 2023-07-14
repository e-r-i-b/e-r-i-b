package com.rssl.phizicgate.manager.services.routable.bankroll;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 09.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceImpl extends RoutableServiceBase implements BankrollService
{
	protected static String GATE_CANNOT_GET_INFORMATION_EXCEPTION = "Невозможно получить информацию из АБС по карте №";

	public BankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		BankrollService delegate = getDelegateFactory(client).service(BankrollService.class);
		return delegate.getClientAccounts(client);
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		GroupResult<String, Account> result = new GroupResult<String, Account>();

		try
		{
			Map<GateFactory, List<String>> map = getDelegateFactory(accountIds);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(accountIds, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				String[] ids = new String[map.get(factory).size()];
				map.get(factory).toArray(ids);
				result.add(factory.service(BankrollService.class).getAccount(ids));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(accountIds, new SystemException("Не возможно получить DelegateFactory"));
		}
		return result;
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		BankrollService delegate = getDelegateFactory(object).service(BankrollService.class);
		return delegate.getAbstract(object, fromDate, toDate, withCardUseInfo);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		GroupResult<Object, AbstractBase> result = new GroupResult<Object, AbstractBase>();
		try
		{
			Map<GateFactory, List<Object>> map = getDelegateFactory(object);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(object, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				result.add(factory.service(BankrollService.class).getAbstract(number, (map.get(factory).toArray())));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(object, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(object, new SystemException("Не возможно получить DelegateFactory"));
		}
		return result;
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		BankrollService delegate = getDelegateFactory(client).service(BankrollService.class);
		return delegate.getClientCards(client);
	}

	public GroupResult<String, Card> getCard(String... cardId)
	{
		GroupResult<String, Card> result = new GroupResult<String, Card>();
		try
		{
			Map<GateFactory, List<String>> map = getDelegateFactory(cardId);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(cardId, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				String[] ids = new String[map.get(factory).size()];
				map.get(factory).toArray(ids);
				result.add(factory.service(BankrollService.class).getCard(ids));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(cardId, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(cardId, new SystemException("Не возможно получить DelegateFactory"));
		}

		return result;
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		GroupResult<Card, List<Card>> result = new GroupResult<Card, List<Card>>();

		try
		{
			Map<GateFactory, List<Card>> map = getDelegateFactory(mainCard);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(mainCard, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				Card[] cards = new Card[map.get(factory).size()];
				map.get(factory).toArray(cards);
				result.add(factory.service(BankrollService.class).getAdditionalCards(cards));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, new SystemException("Не возможно получить DelegateFactory"));
		}
		return result;
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		GroupResult<Card, Account> result = new GroupResult<Card, Account>();
		try
		{
			Map<GateFactory, List<Card>> map = getDelegateFactory(card);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				Card[] cards = new Card[map.get(factory).size()];
				map.get(factory).toArray(cards);
				result.add(factory.service(BankrollService.class).getCardPrimaryAccount(cards));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));
		}

		return result;
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		GroupResult<Card, Client> result = new GroupResult<Card, Client>();
		try
		{
			Map<GateFactory, List<Card>> map = getDelegateFactory(card);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				Card[] cards = new Card[map.get(factory).size()];
				map.get(factory).toArray(cards);
				result.add(factory.service(BankrollService.class).getOwnerInfo(cards));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(card, new SystemException("Не возможно получить DelegateFactory"));
		}
		return result;
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office> ... cardInfo)
	{
		GroupResult<Pair<String, Office>, Client> result = new GroupResult<Pair<String, Office>, Client>();
		try
		{
			Map<GateFactory, List<Pair<String, Office>>> map = getDelegateFactory(false, cardInfo);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(cardInfo, new SystemException("Не возможно получить DelegateFactory"));

			for (GateFactory factory: map.keySet())
			{
				result.add(factory.service(BankrollService.class).getOwnerInfoByCardNumber(map.get(factory).toArray(cardInfo)));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, new SystemException("Не возможно получить DelegateFactory"));
		}
		return result;

	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		GroupResult<Account, Client> result = new GroupResult<Account, Client>();
		try
		{
			Map<GateFactory, List<Account>> map = getDelegateFactory(account);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(account, new SystemException("Не возможно получить DelegateFactory"));

			for(GateFactory factory: map.keySet())
			{
				Account[] accounts = new Account[map.get(factory).size()];
				map.get(factory).toArray(accounts);
				result.add(factory.service(BankrollService.class).getOwnerInfo(accounts));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(account, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(account, new SystemException("Не возможно получить DelegateFactory"));
		}

		return result;
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		BankrollService delegate = getDelegateFactory(account).service(BankrollService.class);
		return delegate.getAccountExtendedAbstract(account, fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		GroupResult<Pair<String, Office>, Account> res = new GroupResult<Pair<String, Office>, Account>();

		// Объектом маршрутизации в данной паре будем считать офис
		try
		{
			Map<GateFactory, List<Pair<String, Office>>> map = getDelegateFactory(false, accountInfo);
			if (map.isEmpty())
				return GroupResultHelper.getOneErrorResult(accountInfo, new SystemException("Не возможно получить DelegateFactory"));

			for (GateFactory factory: map.keySet())
			{
				Pair[] accountInfos = new Pair[map.get(factory).size()];
				map.get(factory).toArray(accountInfos);
				res.add(factory.service(BankrollService.class).getAccountByNumber(accountInfos));
			}
		}
		catch (GateLogicException ignore)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, new SystemException("Не возможно получить DelegateFactory"));
		}
		catch (GateException ignore)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, new SystemException("Не возможно получить DelegateFactory"));
		}

		return res;
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
//		GroupResult<Pair<String,Office>,Card> result = new GroupResult<Pair<String,Office>,Card>();
//		try{
//			Map<GateFactory, List<Pair<String, Office>>> map = getDelegateFactory(cardInfo);
//			for(GateFactory factory: map.keySet())
//			{
//				Pair[] cardInfos = new Pair[map.get(factory).size()];
//				map.get(factory).toArray(cardInfos);
//				result.add(factory.service(BankrollService.class).getCardByNumber(cardInfos));
//			}
//		}catch (GateException e)
//		{
//			throw new RuntimeException(e);
//		}
//		return result;
		// TODO Неизвестно что делать при office =null, но для way4 не актуально.
		throw new UnsupportedOperationException();
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		BankrollService delegate = getDelegateFactory(account).service(BankrollService.class);
		return delegate.getAccHistoryFullExtract(account, fromDate, toDate, withCardUseInfo);
	}
}

