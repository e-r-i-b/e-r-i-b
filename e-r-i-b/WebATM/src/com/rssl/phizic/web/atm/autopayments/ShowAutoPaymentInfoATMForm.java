package com.rssl.phizic.web.atm.autopayments;

import com.rssl.phizic.web.common.client.autopayments.ShowAutoPaymentInfoForm;

/**
 * @author Mescheryakova
 * @ created 11.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoPaymentInfoATMForm extends ShowAutoPaymentInfoForm
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
