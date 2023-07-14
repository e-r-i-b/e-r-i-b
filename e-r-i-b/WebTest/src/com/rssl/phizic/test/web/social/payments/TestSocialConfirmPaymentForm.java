package com.rssl.phizic.test.web.social.payments;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialConfirmPaymentForm extends TestSocialDocumentForm
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

