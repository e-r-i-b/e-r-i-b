package com.rssl.phizic.web.payments;

/**
 * @author Nady
 * @ created 12.08.15
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanClaimForm extends CreateEmployeePaymentForm
{
	private String mobileCountry;
	private String mobileTelecom;
	private String mobileNumber;

	public String getMobileCountry()
	{
		return mobileCountry;
	}

	public void setMobileCountry(String mobileCountry)
	{
		this.mobileCountry = mobileCountry;
	}

	public String getMobileTelecom()
	{
		return mobileTelecom;
	}

	public void setMobileTelecom(String mobileTelecom)
	{
		this.mobileTelecom = mobileTelecom;
	}

	public String getMobileNumber()
	{
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}
}
