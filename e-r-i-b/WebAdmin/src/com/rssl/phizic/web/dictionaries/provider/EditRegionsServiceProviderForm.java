package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.phizic.business.dictionaries.regions.Region;

import java.util.Set;

/**
 * @author hudyakov
 * @ created 15.01.2010
 * @ $Author$ '
 * @ $Revision$
 */

public class EditRegionsServiceProviderForm extends EditServiceProviderFormBase
{
	private String newIds;
	private Set<Region> regions;

	public String getNewIds()
	{
		return newIds;
	}

	public void setNewIds(String newIds)
	{
		this.newIds = newIds;
	}

	public Set<Region> getRegions()
	{
		return regions;
	}

	public void setRegions(Set<Region> regions)
	{
		this.regions = regions;
	}
}
