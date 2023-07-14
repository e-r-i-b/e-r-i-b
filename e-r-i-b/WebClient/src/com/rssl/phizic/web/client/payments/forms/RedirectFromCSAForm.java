package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Gainanov
 * @ created 07.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class RedirectFromCSAForm extends ActionFormBase
{
	private String path;
	private boolean payment;

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public void setPayment(boolean payment)
	{
		this.payment = payment;
	}

	public boolean getPayment()
	{
		return payment;
	}
}
