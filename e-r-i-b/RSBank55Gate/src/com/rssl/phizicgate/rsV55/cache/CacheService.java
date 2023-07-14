package com.rssl.phizicgate.rsV55.cache;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.deposit.DepositService;
import com.rssl.phizic.gate.deposit.Deposit;

import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 25.03.2008
 * @ $Author$
 * @ $Revision$
 */

/*
Сервис для работы с кешем из бизнеса, т.е. для форсированной очистки
 */
public class CacheService
{			
	private static WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
	MessagesCacheManager messagesManager = serviceFacade.getMessagesCacheManager();
	private static BankrollService bankrollService    = GateSingleton.getFactory().service(BankrollService.class);
	private static DepositService depositService    = GateSingleton.getFactory().service(DepositService.class);

	public void clearClientCache(Client client) throws GateException, GateLogicException
	{
		List<Account> accounts = bankrollService.getClientAccounts(client);;
		List<Card> cards = bankrollService.getClientCards( client );
		List<? extends Deposit> deposits = depositService.getClientDeposits(client);

		for (Account account : accounts)
		{
			clearAccountCache( account );
		}

		for (Card card : cards)
		{
			clearCardCache( card );
		}

		for (Deposit deposit : deposits)
		{
			clearDepositCache(deposit);
		}
	}

	public void clearAccountCache(Account account) throws GateException, GateLogicException
	{
		messagesManager.clear(account);
	}

	public void clearCardCache(Card card) throws GateException, GateLogicException
	{
		messagesManager.clear(card);
	}

	public void clearDepositCache(Deposit deposit) throws GateException, GateLogicException
	{
		messagesManager.clear(deposit);
	}
}
