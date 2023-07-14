package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.insurance.PolicyDetails;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class PolicyDetailsImpl implements PolicyDetails
{
	private String series;
	private String num;
	private Calendar issureDt;

	public String getSeries()
	{
		return series;
	}

	public void setSeries(String series)
	{
		this.series = series;
	}

	public String getNum()
	{
		return num;
	}

	public void setNum(String num)
	{
		this.num = num;
	}

	public Calendar getIssureDt()
	{
		return issureDt;
	}

	public void setIssureDt(Calendar issureDt)
	{
		this.issureDt = issureDt;
	}
}