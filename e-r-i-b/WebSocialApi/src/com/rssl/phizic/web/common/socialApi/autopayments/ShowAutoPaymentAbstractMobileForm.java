package com.rssl.phizic.web.common.socialApi.autopayments;

import com.rssl.phizic.web.common.client.autopayments.ShowAutoPaymentInfoForm;

/**
 * Form для отображения графика автоплатежей типа AutoPayment в мобильной версии
 * @author Mescheryakova
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoPaymentAbstractMobileForm extends ShowAutoPaymentInfoForm
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
