package com.rssl.phizic.test.web.social.payments;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialServicePaymentForm extends TestSocialDocumentForm
{
	private String billing;
	private String service;
	private String provider;
	private String barCode;
	private String trustedRecipientId;
	///////////////////////////////////////////////////////////////////////////

	public String getBilling()
	{
		return billing;
	}

	public void setBilling(String billing)
	{
		this.billing = billing;
	}

	public String getService()
	{
		return service;
	}

	public void setService(String service)
	{
		this.service = service;
	}

	public String getProvider()
	{
		return provider;
	}

	public void setProvider(String provider)
	{
		this.provider = provider;
	}

	public String getCodeService()
	{
		return service;
	}

	public void setCodeService(String codeService)
	{
		this.service = codeService;
	}

	public String getBarCode()
	{
		return barCode;
	}

	public void setBarCode(String barCode)
	{
		this.barCode = barCode;
	}

	public String getTrustedRecipientId()
	{
		return trustedRecipientId;
	}

	public void setTrustedRecipientId(String trustedRecipientId)
	{
		this.trustedRecipientId = trustedRecipientId;
	}
}
