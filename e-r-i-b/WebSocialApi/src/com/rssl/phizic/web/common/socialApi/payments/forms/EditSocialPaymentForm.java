package com.rssl.phizic.web.common.socialApi.payments.forms;

import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;

/**
 * @author serkin
 * @ created 19.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditSocialPaymentForm extends EditPaymentForm
{
	private double amount;
	private long SMStemplate;

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public long getSMStemplate()
	{
		return SMStemplate;
	}

	public void setSMStemplate(long SMStemplate)
	{
		this.SMStemplate = SMStemplate;
	}
}
