package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.autopayments.ScheduleItem;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/** детальная информация по копилке
 * @author saharnova
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewMoneyBoxForm extends EditFormBase
{

	private AutoSubscriptionLink link;
	private CardLink cardLink;
	private AccountLink accountLink;
	private List<ScheduleItem> scheduleItems;
	private String textUpdateSheduleItemsError;
	private Long claimId;
	private Long linkId;
	private boolean hasMbConnection;                //имеет ли карта подключение к мобильному банку

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

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	public Long getLinkId()
	{
		return linkId;
	}

	public void setLinkId(Long linkId)
	{
		this.linkId = linkId;
	}

	public Boolean getHasMbConnection()
	{
		return this.hasMbConnection;
	}

	public void setHasMbConnection(boolean hasMbConnection)
	{
		this.hasMbConnection = hasMbConnection;
	}
}
