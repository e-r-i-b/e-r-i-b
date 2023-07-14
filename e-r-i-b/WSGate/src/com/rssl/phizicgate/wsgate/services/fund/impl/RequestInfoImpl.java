package com.rssl.phizicgate.wsgate.services.fund.impl;

import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.gate.fund.RequestInfo;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class RequestInfoImpl implements RequestInfo
{
	private BigDecimal accumulatedSum;
	private Long internalId;
	private FundRequestState state;

	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}

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

	public void setState(String state)
	{
		if (state == null || state.trim().length() == 0)
		{
			return;
		}
		this.state = Enum.valueOf(FundRequestState.class, state);
	}
}
