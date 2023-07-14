package com.rssl.phizic.test.web.atm.payments;

/**
 * @author sergunin
 * @ created 16.01.14
 * @ $Author$
 * @ $Revision$
 */
public class TestATMMakeBillingLongOfferForm extends TestATMDocumentForm
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
