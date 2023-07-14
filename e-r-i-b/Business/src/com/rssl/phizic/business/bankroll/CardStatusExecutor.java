package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.bankroll.CardImpl;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Класс для установки статуса для карт
 * в детальной информации
 * User: miklyaev
 * Date: 03.09.14
 * Time: 10:18
 */
public class CardStatusExecutor extends AbstractService implements BankrollService
{
	public static final String ESB_KEY = BankrollService.class.getName() + ".esb";
	protected static BankrollService delegate;

	public CardStatusExecutor(GateFactory factory) throws GateException
	{
		super(factory);
		delegate = (BankrollService)getDelegate(ESB_KEY);
	}

	public List<Account> getClientAccounts(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Account> getAccount(String... accountIds)
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Account> getAccountByNumber(Pair<String, Office>... accountInfo)
	{
		throw new UnsupportedOperationException();
	}

	public AbstractBase getAbstract(Object object, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Object, AbstractBase> getAbstract(Long number, Object... object)
	{
		throw new UnsupportedOperationException();
	}

	public List<Card> getClientCards(Client client) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Card> getCard(String... cardIds)
	{
		GroupResult<String, Card> delegateResult = delegate.getCard(cardIds);
		setArrestedCardState(delegateResult);
		return delegateResult;
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

	public AccountAbstract getAccountExtendedAbstract(Account account, Calendar fromDate, Calendar toDate) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Pair<String, Office>, Card> getCardByNumber(Client client, Pair<String, Office>... cardInfo)
	{
		GroupResult<Pair<String, Office>, Card> delegateResult = delegate.getCardByNumber(client, cardInfo);
		setArrestedCardState(delegateResult);
		return delegateResult;
	}

	public AccountAbstract getAccHistoryFullExtract(Account account, Calendar fromDate, Calendar toDate, Boolean withCardUseInfo) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	private void setArrestedCardState(GroupResult delegateResult)
	{
		if (PersonContext.isAvailable() && delegateResult != null)
		{
			Set<String> arrestedCards = PersonContext.getPersonDataProvider().getPersonData().getClientArrestedCards();
			for (Object key : delegateResult.getKeys())
			{
				CardImpl card = (CardImpl)delegateResult.getResult(key);
				if (card != null && CollectionUtils.isNotEmpty(arrestedCards) && arrestedCards.contains(card.getNumber()))
					card.setCardAccountState(AccountState.ARRESTED);
			}
		}
	}
}
