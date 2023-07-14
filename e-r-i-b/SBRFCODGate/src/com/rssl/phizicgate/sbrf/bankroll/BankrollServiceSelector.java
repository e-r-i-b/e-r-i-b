package com.rssl.phizicgate.sbrf.bankroll;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.GateSettingsConfig;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.Calendar;
import java.util.List;

/**
 * Класс для передачи управления в зависимости от использования нового сервиса DepoCod
 * @author miklyaev
 * @ created 25.04.14
 * @ $Author$
 * @ $Revision$
 */

public class BankrollServiceSelector extends RoutableServiceBase implements BankrollService
{
	private BankrollService delegateCod;
	private BankrollService delegateDepoCod;

	private static final String COD_DELEGATE_KEY = ".cod";
	private static final String DEPO_COD_DELEGATE_KEY = ".DepoCod";

	public BankrollServiceSelector(GateFactory factory)
	{
		super(factory);
		delegateCod = (BankrollService) getDelegate(BankrollService.class.getName() + COD_DELEGATE_KEY);
		delegateDepoCod = (BankrollService) getDelegate(BankrollService.class.getName() + DEPO_COD_DELEGATE_KEY);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		return delegateCod.getClientAccounts(client);
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		return delegateCod.getAccount(accountIds);
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		return delegateCod.getAccountByNumber(accountInfo);
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		if (ConfigFactory.getConfig(GateSettingsConfig.class).useDepoCodWS())
			return delegateDepoCod.getAbstract(object, fromDate, toDate, withCardUseInfo);
		return delegateCod.getAbstract(object, fromDate, toDate, withCardUseInfo);
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		if (ConfigFactory.getConfig(GateSettingsConfig.class).useDepoCodWS())
			return delegateDepoCod.getAbstract(number, object);
		return delegateCod.getAbstract(number, object);
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		return delegateCod.getClientCards(client);
	}

	public GroupResult<String, Card> getCard(String... cardIds)
	{
		return delegateCod.getCard(cardIds);
	}

	public GroupResult<Card, List<Card>> getAdditionalCards(Card... mainCard)
	{
		return delegateCod.getAdditionalCards(mainCard);
	}

	public GroupResult<Card, Account> getCardPrimaryAccount(Card... card)
	{
		return delegateCod.getCardPrimaryAccount(card);
	}

	public GroupResult<Card, Client> getOwnerInfo(Card... card)
	{
		return delegateCod.getOwnerInfo(card);
	}

	public GroupResult<Pair<String, Office>, Client> getOwnerInfoByCardNumber(Pair<String, Office>... cardInfo)
	{
		return delegateCod.getOwnerInfoByCardNumber(cardInfo);
	}

	public GroupResult<Account, Client> getOwnerInfo(Account... account)
	{
		return delegateCod.getOwnerInfo(account);
	}

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		if (ConfigFactory.getConfig(GateSettingsConfig.class).useDepoCodWS())
			return delegateDepoCod.getAccountExtendedAbstract(account, fromDate, toDate);
		return delegateCod.getAccountExtendedAbstract(account, fromDate, toDate);
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		return delegateCod.getCardByNumber(client, cardInfo);
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		return delegateDepoCod.getAccHistoryFullExtract(account, fromDate, toDate, withCardUseInfo);
	}
}
