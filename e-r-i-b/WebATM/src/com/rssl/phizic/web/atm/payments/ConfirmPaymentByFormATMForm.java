package com.rssl.phizic.web.atm.payments;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author Dorzhinov
 * @ created 24.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmPaymentByFormATMForm extends ConfirmPaymentByFormForm
{
	private String operation;

	public String getOperation()
	{
		return operation;
	}

	public void setOperation(String operation)
	{
		this.operation = operation;
	}
}
