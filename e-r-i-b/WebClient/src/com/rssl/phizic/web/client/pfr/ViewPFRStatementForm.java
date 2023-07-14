package com.rssl.phizic.web.client.pfr;

import java.util.Calendar;

/**
 * @author Dorzhinov
 * @ created 07.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ViewPFRStatementForm extends PFRStatementBaseForm
{
	private String SNILS;
	private Calendar claimDate;
	private Long claimId;

	public String getSNILS()
	{
		return SNILS;
	}

	public void setSNILS(String SNILS)
	{
		this.SNILS = SNILS;
	}

	public Calendar getClaimDate()
	{
		return claimDate;
	}

	public void setClaimDate(Calendar claimDate)
	{
		this.claimDate = claimDate;
	}

	public Long getClaimId()
	{
		return claimId;
	}

	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}
}
