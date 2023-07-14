package com.rssl.phizic.wsgate.fund.types;

import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.fund.Response;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 21.09.14
 * @ $Author$
 * @ $Revision$
 */
public class ResponseImpl implements Response
{
	private String externalId;
	private String externalRequestId;
	private FundResponseState state;
	private String message;
	private BigDecimal sum;

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getExternalRequestId()
	{
		return externalRequestId;
	}

	public void setExternalRequestId(String externalRequestId)
	{
		this.externalRequestId = externalRequestId;
	}

	public FundResponseState getState()
	{
		return state;
	}

	public void setState(FundResponseState state)
	{
		this.state = state;
	}

	public void setState(String state)
	{
		if (state == null || state.trim().length() == 0)
		{
			return;
		}
		this.state = Enum.valueOf(FundResponseState.class, state);
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public BigDecimal getSum()
	{
		return sum;
	}

	public void setSum(BigDecimal sum)
	{
		this.sum = sum;
	}
}
