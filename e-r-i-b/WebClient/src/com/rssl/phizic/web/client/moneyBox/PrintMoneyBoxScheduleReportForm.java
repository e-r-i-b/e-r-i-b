package com.rssl.phizic.web.client.moneyBox;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author saharnova
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

public class PrintMoneyBoxScheduleReportForm extends EditFormBase
{
	private AutoSubscriptionLink link;
	private CardLink cardLink;
	private AccountLink accountLink;
	private List<ScheduleItem> scheduleItems;
	private String textUpdateSheduleItemsError;

	public AutoSubscriptionLink getLink()
	{
		return link;
	}

	public void setLink(AutoSubscriptionLink link)
	{
		this.link = link;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public AccountLink getAccountLink()
	{
		return accountLink;
	}

	public void setAccountLink(AccountLink accountLink)
	{
		this.accountLink = accountLink;
	}

	public List<ScheduleItem> getScheduleItems()
	{
		return scheduleItems;
	}

	public void setScheduleItems(List<ScheduleItem> scheduleItems)
	{
		this.scheduleItems = scheduleItems;
	}

	public String getTextUpdateSheduleItemsError()
	{
		return textUpdateSheduleItemsError;
	}

	public void setTextUpdateSheduleItemsError(String textUpdateSheduleItemsError)
	{
		this.textUpdateSheduleItemsError = textUpdateSheduleItemsError;
	}
}
