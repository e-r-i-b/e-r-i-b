package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;

import java.util.Calendar;

/**
 * @author Dorzhinov
 * @ created 04.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ViewPFRStatementOperation extends PFRStatementBaseOperation
{
	private Long claimId;
	private Calendar claimDate;

	public void initialize(Long claimId) throws BusinessException
	{
		super.initialize(claimId);

		this.claimId = claimId;
		claimDate = documentService.findById(claimId).getDateCreated();
	}

	public String getSNILS()
	{
		return PersonContext.getPersonDataProvider().getPersonData().getPerson().getSNILS();
	}

	public Calendar getClaimDate()
	{
		return claimDate;
	}

	public Long getClaimId()
	{
		return claimId;
	}
}
