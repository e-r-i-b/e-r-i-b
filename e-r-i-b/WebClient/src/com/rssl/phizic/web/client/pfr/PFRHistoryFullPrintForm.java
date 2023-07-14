package com.rssl.phizic.web.client.pfr;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Calendar;

/**
 * Форма печати справки о видах и размерах пенсий
 * @author Jatsky
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullPrintForm extends EditFormBase
{
	private String typePeriod;
	private String fromDateString;
	private String toDateString;
	private Account account;
	private Card card;
	private Calendar fromDate;
	private Calendar toDate;
	private AccountAbstract accountAbstract;
	private String fromResource;
	private Client client;

	public String getTypePeriod()
	{
		return typePeriod;
	}

	public void setTypePeriod(String typePeriod)
	{
		this.typePeriod = typePeriod;
	}

	public String getFromDateString()
	{
		return fromDateString;
	}

	public void setFromDateString(String fromDateString)
	{
		this.fromDateString = fromDateString;
	}

	public String getToDateString()
	{
		return toDateString;
	}

	public void setToDateString(String toDateString)
	{
		this.toDateString = toDateString;
	}

	public Account getAccount()
	{
		return account;
	}

	public void setAccount(Account account)
	{
		this.account = account;
	}

	public Card getCard()
	{
		return card;
	}

	public void setCard(Card card)
	{
		this.card = card;
	}

	public Calendar getFromDate()
	{
		return fromDate;
	}

	public void setFromDate(Calendar fromDate)
	{
		this.fromDate = fromDate;
	}

	public Calendar getToDate()
	{
		return toDate;
	}

	public void setToDate(Calendar toDate)
	{
		this.toDate = toDate;
	}

	public AccountAbstract getAccountAbstract()
	{
		return accountAbstract;
	}

	public void setAccountAbstract(AccountAbstract accountAbstract)
	{
		this.accountAbstract = accountAbstract;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}
}
