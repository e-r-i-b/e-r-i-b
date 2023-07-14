package com.rssl.phizic.web.common.socialApi.payments;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author Dorzhinov
 * @ created 24.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmSocialPaymentByFormForm extends ConfirmPaymentByFormForm
{
	private String operation;
	private boolean confirmAutoStart;
	
	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	public boolean isConfirmAutoStart()
	{
		return confirmAutoStart;
	}

	public void setConfirmAutoStart(boolean confirmAutoStart)
	{
		this.confirmAutoStart = confirmAutoStart;
	}
}
