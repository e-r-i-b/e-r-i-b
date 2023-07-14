package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogFormBase;
import com.rssl.phizic.web.actions.payments.forms.ContainRegionGuidActionFormInterface;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCategoryATMForm extends ApiCatalogFormBase implements ContainRegionGuidActionFormInterface
{

	private String region;
	private String regionGuid;

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}

	public Long getRegionId()
	{
		return region != null ? Long.parseLong(region) : null;
	}

	public String getRegionGuid()
	{
		return regionGuid;
	}

	public void setRegionId(Long regionId)
	{
		this.region = regionId != null ? regionId.toString() : null;
	}

	public void setRegionGuid(String regionGuid)
	{
		this.regionGuid = regionGuid;
	}
}
