package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.utils.GroupResultHelper;

import java.util.*;
import java.lang.reflect.Method;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServiceManager extends AbstractService implements BankrollService
{
	BankrollServicesXmlRouter xmlRouter;

	public BankrollServiceManager(GateFactory factory) throws GateException
	{
		super(factory);
		xmlRouter = new BankrollServicesXmlRouter(factory);
	}

	public GroupResult<Object, AbstractBase> getAbstract(java.lang.Long number, Object... object)
	{
		try
		{
			Object[] methodParameters = new Object[]{number, object};
			Method currentMethod = this.getClass().getMethod("getAbstract", Long.class, Object[].class);

			return (GroupResult<Object, AbstractBase>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(object, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(object, e);
		}
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		try
		{
			Object[] methodParameters = new Object[]{object, fromDate, toDate, withCardUseInfo};
			Method currentMethod = this.getClass().getMethod("getAbstract", Object.class, Calendar.class, Calendar.class, Boolean.class);

			return (AbstractBase) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			throw new GateException("Ошибка получения метода. " + e);
		}
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		try
		{
			Object[] methodParameters = new Object[]{client};
			Method currentMethod = this.getClass().getMethod("getClientCards", Client.class);

			return (List<Card>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			throw new GateException("Ошибка получения метода. " + e);
		}
	}

	public GroupResult<String, Card> getCard(String... cardId)
	{
		try
		{
			Object[] methodParameters = new Object[]{cardId};
			Method currentMethod = this.getClass().getMethod("getCard", String[].class);

			return (GroupResult<String, Card>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(cardId, new GateException("Ошибка получения метода.", e));
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
			Object[] methodParameters = new Object[]{mainCard};
			Method currentMethod = this.getClass().getMethod("getAdditionalCards", Card[].class);

			return (GroupResult<Card, List<Card>>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(mainCard, new GateException("Ошибка получения метода.", e));
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
			Object[] methodParameters = new Object[]{card};
			Method currentMethod = this.getClass().getMethod("getCardPrimaryAccount", Card[].class);

			return (GroupResult<Card, Account>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(card, new GateException("Ошибка получения метода.", e));
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
			Object[] methodParameters = new Object[]{card};
			Method currentMethod = this.getClass().getMethod("getOwnerInfo", Card[].class);

			return (GroupResult<Card, Client>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(card, new GateException("Ошибка получения метода.", e));
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
			Object[] methodParameters = new Object[]{cardInfo};
			Method currentMethod = this.getClass().getMethod("getOwnerInfoByCardNumber", Pair[].class);

			return (GroupResult<Pair<String, Office>, Client>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
		}
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		try
		{
			Object[] methodParameters = new Object[]{client};
			Method currentMethod = this.getClass().getMethod("getClientAccounts", Client.class);

			return (List<Account>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			throw new GateException("Ошибка получения метода. " + e);
		}
	}

	public GroupResult<String, Account> getAccount(String... accountId)
	{
		try
		{
			Object[] methodParameters = new Object[]{accountId};
			Method currentMethod = this.getClass().getMethod("getAccount", String[].class);

			return (GroupResult<String, Account>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(accountId, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountId, e);
		}
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		try
		{
			Object[] methodParameters = new Object[]{account};
			Method currentMethod = this.getClass().getMethod("getOwnerInfo", Account[].class);

			return (GroupResult<Account, Client>) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(account, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(account, e);
		}
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		try
		{
			Object[] methodParameters = new Object[]{account,fromDate,toDate};
			Method currentMethod = this.getClass().getMethod("getAccountExtendedAbstract", Account.class, Calendar.class, Calendar.class);

			return (AccountAbstract) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			throw new GateException("Ошибка получения метода. " + e);
		}
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		try
		{
			Object[] methodParameters = new Object[]{accountInfo};
			Method currentMethod = this.getClass().getMethod("getAccountByNumber", Pair[].class);

			return (GroupResult) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(accountInfo, e);
		}
	}

	public GroupResult<Pair<String,Office>,Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		try
		{
			Object[] methodParameters = new Object[]{client, cardInfo};
			Method currentMethod = this.getClass().getMethod("getCardByNumber", Client.class, Pair[].class);

			return (GroupResult) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, new GateException("Ошибка получения метода.", e));
		}
		catch (IKFLException e)
		{
			return GroupResultHelper.getOneErrorResult(cardInfo, e);
		}
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		try
		{
			Object[] methodParameters = new Object[]{account, fromDate, toDate, withCardUseInfo};
			Method currentMethod = this.getClass().getMethod("getAccHistoryFullExtract", Account.class, Calendar.class, Calendar.class, Boolean.class);

			return (AccountAbstract) xmlRouter.getBankrollObject(currentMethod, methodParameters);
		}
		catch (NoSuchMethodException e)
		{
			throw new GateException("Ошибка получения метода. " + e);
		}
	}
}
