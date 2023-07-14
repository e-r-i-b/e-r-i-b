package com.rssl.phizic.test.web.social.payments;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialMakeBillingLongOfferForm extends TestSocialServicePaymentForm
{
	private String autoPaymentStartDate;
	private String firstPaymentDate;

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
}

