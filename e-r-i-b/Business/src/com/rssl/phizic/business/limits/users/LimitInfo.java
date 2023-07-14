package com.rssl.phizic.business.limits.users;

import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.business.limits.RestrictionType;

/**
 * @author niculichev
 * @ created 14.01.14
 * @ $Author$
 * @ $Revision$
 */
public class LimitInfo
{
	private Long id;
	private String externaId;
	private LimitType limitType;
	private RestrictionType restrictionType;
	private String externalGroupRisk;

	public LimitInfo(LimitType limitType, RestrictionType restrictionType, String externalGroupRisk)
	{
		this.limitType = limitType;
		this.restrictionType = restrictionType;
		this.externalGroupRisk = externalGroupRisk;
	}

	public LimitInfo()
	{
	}

	public LimitInfo(LimitInfo limitInfo)
	{
		this.limitType = limitInfo.getLimitType();
		this.restrictionType = limitInfo.getRestrictionType();
		this.externalGroupRisk = limitInfo.getExternalGroupRisk();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getExternaId()
	{
		return externaId;
	}

	public void setExternaId(String externaId)
	{
		this.externaId = externaId;
	}

	public LimitType getLimitType()
	{
		return limitType;
	}

	public void setLimitType(LimitType limitType)
	{
		this.limitType = limitType;
	}

	public RestrictionType getRestrictionType()
	{
		return restrictionType;
	}

	public void setRestrictionType(RestrictionType restrictionType)
	{
		this.restrictionType = restrictionType;
	}

	public String getExternalGroupRisk()
	{
		return externalGroupRisk;
	}

	public void setExternalGroupRisk(String externalGroupRisk)
	{
		this.externalGroupRisk = externalGroupRisk;
	}
}
