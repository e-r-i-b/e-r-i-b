package com.rssl.phizic.web.atm.dictionaries;

import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowRegionsListATMForm extends ActionFormBase
{
	private Long parentId;
	private Region region; //������� ������
	private List<Region> regions; //������ ���������������� ��������

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
