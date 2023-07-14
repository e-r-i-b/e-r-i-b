package com.rssl.phizic.web.common.mobile.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.web.common.client.ext.sbrf.security.SelectRegionForm;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListMobileForm extends SelectRegionForm
{
	private Long parentId;
	private Region region; //текущий регион
	private List<Region> regions; //список непосредственных потомков

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public Region getRegion()
	{
		return region;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

	public List<Region> getRegions()
	{
		return regions;
	}

	public void setRegions(List<Region> regions)
	{
		this.regions = regions;
	}
}
