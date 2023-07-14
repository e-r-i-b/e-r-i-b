package com.rssl.phizic.web.common.socialApi.autosubscriptions;

import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;

/**
 * Form ��� ����������� ������� ������������ ���� AutoSubscription � ��������� ������
 * @ author Rtischeva
 * @ created 08.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoSubscriptionAbstractMobileForm extends ShowAutoSubscriptionInfoForm
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
