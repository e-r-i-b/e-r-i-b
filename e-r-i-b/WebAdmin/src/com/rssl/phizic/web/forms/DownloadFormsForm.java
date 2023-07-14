package com.rssl.phizic.web.forms;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Kidyaev
 * @ created 28.02.2006
 * @ $Author: erkin $
 * @ $Revision: 24884 $
 */
public class DownloadFormsForm extends ActionFormBase
{
	private String paymentFormName;
	private String description;

	public String getPaymentFormName()
	{
		return paymentFormName;
	}

	public void setPaymentFormName(String paymentFormName)
	{
		this.paymentFormName = paymentFormName;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
