package com.rssl.phizic.common.type;

import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 @author: EgorovaA
 @ created: 27.12.2012
 @ $Author$
 @ $Revision$
 */
public class ErmbProfileIdentityCard
{
	private String idType;
	private String idSeries;
	private String idNum;
	private String issuedBy;
	private Calendar issueDt;

	public String getIdType()
	{
		return idType;
	}

	public void setIdType(String idType)
	{
		this.idType = idType;
	}

	public String getIdSeries()
	{
		return idSeries;
	}

	public void setIdSeries(String idSeries)
	{
		this.idSeries = idSeries;
	}

	public String getIdNum()
	{
		return idNum;
	}

	public void setIdNum(String idNum)
	{
		this.idNum = idNum;
	}

	public String getIssuedBy()
	{
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy)
	{
		this.issuedBy = issuedBy;
	}

	public Calendar getIssueDt()
	{
		return issueDt;
	}

	public void setIssueDt(Calendar issueDt)
	{
		this.issueDt = issueDt;
	}

	public boolean isEmpty()
	{
		if (StringHelper.isNotEmpty(getIdType()))
			return false;
		if (StringHelper.isNotEmpty(getIdSeries()))
			return false;
		if (StringHelper.isNotEmpty(getIdNum()))
			return false;
		if (StringHelper.isNotEmpty(getIssuedBy()))
			return false;
		if (getIssueDt() != null)
			return false;
		return true;
	}
}
