package com.rssl.phizic.test.web.atm.payments;

/**
 * @author Mescheryakova
 * @ created 20.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestATMRefuseAutoPaymentForm extends TestATMDocumentForm
{
	private String linkId;      // id ����������� �����������

	public String getLinkId()
	{
		return linkId;
	}

	public void setLinkId(String linkId)
	{
		this.linkId = linkId;
	}
}