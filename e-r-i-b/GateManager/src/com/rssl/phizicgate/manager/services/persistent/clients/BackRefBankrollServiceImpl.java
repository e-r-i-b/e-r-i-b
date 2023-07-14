package com.rssl.phizicgate.manager.services.persistent.clients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardNotFoundException;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

/**
 * @author Krenev
 * @ created 29.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankrollServiceImpl extends PersistentServiceBase<BackRefBankrollService> implements BackRefBankrollService
{
	public BackRefBankrollServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public String findAccountExternalId(String accountNumber) throws GateException, GateLogicException
	{
		String id = delegate.findAccountExternalId(accountNumber);
		if (id == null)
		{
			return null;
		}
		return removeRouteInfo(id);
	}

	public String findCardExternalId(String cardNumber) throws GateException, GateLogicException
	{
		String id = delegate.findCardExternalId(cardNumber);
		if (id == null)
		{
			return null;
		}
		return removeRouteInfo(id);
	}

	public String findCardExternalId(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		String id = delegate.findCardExternalId(loginId, cardNumber);
		if (id == null)
		{
			return null;
		}
		return removeRouteInfo(id);
	}

	public String findAccountBusinessOwner(Account account) throws GateException, GateLogicException
	{
		String owner = delegate.findAccountBusinessOwner(storeRouteInfo(account));
		if (owner == null)
		{
			return null;
		}
		return removeRouteInfo(owner);
	}

	public Office getAccountOffice(Long loginId, String accountNumber) throws GateException, GateLogicException
	{
		Office office = delegate.getAccountOffice(loginId, accountNumber);
		if (office == null)
		{
			return null;
		}
		return removeRouteInfo(office);
	}

	public Office getCardOffice(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		Office office = delegate.getCardOffice(loginId, cardNumber);
		if (office == null)
		{
			return null;
		}
		return removeRouteInfo(office);
	}

	public Account getCardAccount(String cardNumber) throws GateException, GateLogicException
	{
		Account cardAccount = delegate.getCardAccount(cardNumber);
		if (cardAccount == null)
		{
			return null;
		}
		return removeRouteInfo(cardAccount);
	}

	public Account getCardAccount(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		Account cardAccount = delegate.getCardAccount(loginId, cardNumber);
		if (cardAccount == null)
		{
			return null;
		}
		return removeRouteInfo(cardAccount);
	}

	public Card getStoredCard(Long loginId, String cardNumber) throws GateException, GateLogicException
	{
		Card storedCard = delegate.getStoredCard(loginId, cardNumber);
		if(storedCard == null)
		{
			return null;
		}
		return removeRouteInfo(storedCard);
	}
}