package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author bogdanov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionPaymentInfoForm extends EditFormBase
{
	private AutoSubscriptionDetailInfo payment;
	private AutoSubscriptionLink subscriptionLink;
	private CardLink cardLink;
	private long subscriptionId;

	public AutoSubscriptionDetailInfo getPayment()
	{
		return payment;
	}

	public void setPayment(AutoSubscriptionDetailInfo payment)
	{
		this.payment = payment;
	}

	public AutoSubscriptionLink getSubscriptionLink()
	{
		return subscriptionLink;
	}

	public void setSubscriptionLink(AutoSubscriptionLink subscriptionLink)
	{
		this.subscriptionLink = subscriptionLink;
	}

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public long getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}
}
