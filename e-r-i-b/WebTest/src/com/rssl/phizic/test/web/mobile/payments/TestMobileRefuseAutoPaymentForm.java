package com.rssl.phizic.test.web.mobile.payments;

/**
 * @author Mescheryakova
 * @ created 20.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestMobileRefuseAutoPaymentForm extends TestMobileDocumentForm
{
	private String linkId;      // id отменяемого автоплатежа

	public String getLinkId()
	{
		return linkId;
	}

	public void setLinkId(String linkId)
	{
		this.linkId = linkId;
	}
}
