package com.rssl.phizic.web.atm.autosubscriptions;

import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;

/**
 * Form для отображения графика автоплатежей типа AutoSubscription в мобильной версии
 * @ author Rtischeva
 * @ created 08.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoSubscriptionAbstractATMForm extends ShowAutoSubscriptionInfoForm
{
	private String from;
	private String to;

	public String getFrom()
	{
		return from;
	}

	public void setFrom(String from)
	{
		this.from = from;
	}

	public String getTo()
	{
		return to;
	}

	public void setTo(String to)
	{
		this.to = to;
	}

}
