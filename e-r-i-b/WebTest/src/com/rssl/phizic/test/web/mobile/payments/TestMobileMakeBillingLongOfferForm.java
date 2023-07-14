package com.rssl.phizic.test.web.mobile.payments;

/**
 * @author Dorzhinov
 * @ created 27.09.2013
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileMakeBillingLongOfferForm extends TestMobileServicePaymentForm
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
