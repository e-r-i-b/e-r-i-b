package com.rssl.phizic.business.dictionaries.bankroll;

import com.rssl.phizic.business.resources.external.CardLink;

import java.util.Calendar;

/**
 * @author egorova
 * @ created 08.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class SendedAbstract
{
	private Long id;
	private CardLink cardLink;
	private Calendar sendedDate;
	private Calendar fromDate;
	private Calendar toDate;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public Calendar getSendedDate()
	{
		return sendedDate;
	}

	public void setSendedDate(Calendar sendedDate)
	{
		this.sendedDate = sendedDate;
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
}
