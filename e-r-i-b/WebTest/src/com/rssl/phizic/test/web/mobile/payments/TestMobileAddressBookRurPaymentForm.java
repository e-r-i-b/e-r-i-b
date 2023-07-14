package com.rssl.phizic.test.web.mobile.payments;

/**
 * Форма универсального перевода на карту частному лицу с использованием адресной книги
 * @ author: Gololobov
 * @ created: 31.10.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileAddressBookRurPaymentForm extends TestMobileRurPaymentForm
{
	private String externalCardNumber;
	private String externalPhoneNumber;
	private String externalContactId;

	public String getExternalCardNumber()
	{
		return externalCardNumber;
	}

	public void setExternalCardNumber(String externalCardNumber)
	{
		this.externalCardNumber = externalCardNumber;
	}

	public String getExternalPhoneNumber()
	{
		return externalPhoneNumber;
	}

	public void setExternalPhoneNumber(String externalPhoneNumber)
	{
		this.externalPhoneNumber = externalPhoneNumber;
	}

	public String getExternalContactId()
	{
		return externalContactId;
	}

	public void setExternalContactId(String externalContactId)
	{
		this.externalContactId = externalContactId;
	}
}
