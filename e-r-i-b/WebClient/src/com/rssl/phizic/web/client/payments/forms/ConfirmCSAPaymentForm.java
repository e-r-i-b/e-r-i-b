package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.web.actions.payments.forms.ConfirmPaymentByFormForm;

/**
 * @author Gainanov
 * @ created 12.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAPaymentForm  extends ConfirmPaymentByFormForm
{
	private String CSAPath;

	public String getCSAPath()
	{
		return CSAPath;
	}

	public void setCSAPath(String CSAPath)
	{
		this.CSAPath = CSAPath;
	}
}
