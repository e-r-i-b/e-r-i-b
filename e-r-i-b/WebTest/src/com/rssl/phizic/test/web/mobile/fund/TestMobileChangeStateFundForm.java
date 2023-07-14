package com.rssl.phizic.test.web.mobile.fund;

import com.rssl.phizic.test.web.mobile.payments.TestMobileDocumentForm;

/**
 * @author osminin
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileChangeStateFundForm extends TestMobileDocumentForm
{
	private String externalId;
	private String state;
	private String sum;
	private String message;
	private String closeConfirm;
	private String fromResource;

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getSum()
	{
		return sum;
	}

	public void setSum(String sum)
	{
		this.sum = sum;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getCloseConfirm()
	{
		return closeConfirm;
	}

	public void setCloseConfirm(String closeConfirm)
	{
		this.closeConfirm = closeConfirm;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}
}
