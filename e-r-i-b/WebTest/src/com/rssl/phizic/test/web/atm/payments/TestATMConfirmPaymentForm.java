package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Dorzhinov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMConfirmPaymentForm extends TestATMDocumentForm
{
	private String confirmSmsPassword;
	private String confirmCardPassword;

	public String getConfirmSmsPassword()
	{
		return confirmSmsPassword;
	}

	public void setConfirmSmsPassword(String confirmSmsPassword)
	{
		this.confirmSmsPassword = confirmSmsPassword;
	}

	public String getConfirmCardPassword()
	{
		return confirmCardPassword;
	}

	public void setConfirmCardPassword(String confirmCardPassword)
	{
		this.confirmCardPassword = confirmCardPassword;
	}
}