package com.rssl.phizic.test.web.mobile.payments;

/**
 * User: Moshenko
 * Date: 13.02.12
 * Time: 15:42
 */
public class TestMobileIMAPaymentPaymentForm  extends TestMobileInternalPaymentForm
{
	private String documentNumber;
	private String documentDate;

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(String documentDate)
	{
		this.documentDate = documentDate;
	}
}
