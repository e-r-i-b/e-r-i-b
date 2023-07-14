package com.rssl.phizic.wsgate.fund.types;

import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.gate.fund.Request;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 21.09.14
 * @ $Author$
 * @ $Revision$
 */
public class RequestImpl implements Request
{
	private String externalId;
	private FundRequestState state;
	private BigDecimal requiredSum;
	private BigDecimal reccomendSum;
	private String message;
	private String resource;
	private Calendar closedDate;
	private Calendar expectedClosedDate;
	private Calendar createdDate;

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
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

	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	public BigDecimal getReccomendSum()
	{
		return reccomendSum;
	}

	public void setReccomendSum(BigDecimal reccomendSum)
	{
		this.reccomendSum = reccomendSum;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public Calendar getClosedDate()
	{
		return closedDate;
	}

	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	public Calendar getExpectedClosedDate()
	{
		return expectedClosedDate;
	}

	public void setExpectedClosedDate(Calendar expectedClosedDate)
	{
		this.expectedClosedDate = expectedClosedDate;
	}

	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}
}
