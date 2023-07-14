package com.rssl.phizic.business.advertising;

/**
 * Enum областей рекламного баннера.
 * @author lepihina
 * @ created 10.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingArea implements AdvertisingOrderedField
{
	private Long id; // идентификатор
	private String areaName; // область
    private Long orderIndex; // порядок отображения

	public AdvertisingArea()
	{
	}

	public AdvertisingArea(String areaName, Long orderIndex)
	{
		this.areaName = areaName;
		this.orderIndex = orderIndex;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String name)
	{
		this.areaName = name;
	}

	public Long getOrderIndex()
	{
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}
}
