package com.rssl.phizic.test.web.mobile.payments;

/**
 * @author Pankin
 * @ created 19.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestMobileAccountOpeningClaimForm extends TestMobileDocumentForm
{
	private String depositId;
	private String depositType;
	private String depositGroup;

	public String getDepositId()
	{
		return depositId;
	}

	public void setDepositId(String depositId)
	{
		this.depositId = depositId;
	}

	public String getDepositType()
	{
		return depositType;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public String getDepositGroup()
	{
		return depositGroup;
	}

	public void setDepositGroup(String depositGroup)
	{
		this.depositGroup = depositGroup;
	}
}
