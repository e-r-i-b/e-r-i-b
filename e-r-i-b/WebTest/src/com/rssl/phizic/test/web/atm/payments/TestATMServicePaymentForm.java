package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Erkin
 * @ created 02.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMServicePaymentForm extends TestATMDocumentForm
{
	private String billing;

	private String service;

	private String provider;

	private String recipient;

	private String  autoPaymentStartDate;

	private String firstPaymentDate;

	private String barCode;

	private String serviceGuid;
	private String providerGuid;
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

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public String getCodeService()
	{
		return service;
	}

	public void setCodeService(String codeService)
	{
		this.service = codeService;
	}

	public String getAutoPaymentStartDate()
	{
		return autoPaymentStartDate;
	}

	public void setAutoPaymentStartDate(String autoPaymentStartDate)
	{
		this.autoPaymentStartDate = autoPaymentStartDate;
	}

	public String getFirstPaymentDate()
	{
		return firstPaymentDate;
	}

	public void setFirstPaymentDate(String firstPaymentDate)
	{
		this.firstPaymentDate = firstPaymentDate;
	}

	public String getBarCode()
	{
		return barCode;
	}

	public String getServiceGuid()
	{
		return serviceGuid;
	}

	public void setServiceGuid(String serviceGuid)
	{
		this.serviceGuid = serviceGuid;
	}

	public String getProviderGuid()
	{
		return providerGuid;
	}

	public void setProviderGuid(String providerGuid)
	{
		this.providerGuid = providerGuid;
	}

	public void setBarCode(String barCode)
	{
		this.barCode = barCode;
	}
}