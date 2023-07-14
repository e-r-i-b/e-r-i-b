package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author emakarov
 * @ created 09.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class AddLoanClaimCommentForm extends ActionFormBase
{
	private String comment;
	private Long[] claimIds;

	public String getComment()
	{
		return comment;
	}

	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Long[] getClaimIds()
	{
		return claimIds;
	}

	public void setClaimIds(Long[] claimIds)
	{
		this.claimIds = claimIds;
	}
}
