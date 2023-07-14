package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.FundRequestState;

/**
 * @author osminin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class FundRequestInfo
{
	private Long internalId;
	private FundRequestState state;

	public Long getInternalId()
	{
		return internalId;
	}

	public void setInternalId(Long internalId)
	{
		this.internalId = internalId;
	}

	public FundRequestState getState()
	{
		return state;
	}

	public void setState(FundRequestState state)
	{
		this.state = state;
	}
}
