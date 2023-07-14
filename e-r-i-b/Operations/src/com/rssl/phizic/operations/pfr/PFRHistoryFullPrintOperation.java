package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Опреация печати справки о видах и размерах пенсий
 * @author Jatsky
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullPrintOperation extends OperationBase implements ListEntitiesOperation
{
	private Account account;
	private Client client;
	private Card card;

	public void initialize(final String accCode) throws BusinessException, BusinessLogicException
	{
		String[]   fromResourceCode = StringHelper.isNotEmpty(accCode) ? accCode.split(":") : new String[0];
		PersonData personData       = PersonContext.getPersonDataProvider().getPersonData();

		if (fromResourceCode.length == 2)
		{
			if (fromResourceCode[0].equals(AccountLink.CODE_PREFIX))
			{
				AccountLink accountLink = personData.getAccount(Long.valueOf(fromResourceCode[1]));
				account = accountLink.getAccount();
			}
			else if (fromResourceCode[0].equals(CardLink.CODE_PREFIX))
			{
				CardLink cardLink = personData.getCard(Long.valueOf(fromResourceCode[1]));
				card    = cardLink.getCard();
				account = cardLink.getCardAccount();
			}
		}

		client = personData.getPerson().asClient();
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public AccountAbstract getAbstract(Calendar fromDate, Calendar toDate) throws BusinessLogicException, BusinessException
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
		try
		{
			return bankrollService.getAccHistoryFullExtract(getAccount(), fromDate, toDate, Boolean.FALSE);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
