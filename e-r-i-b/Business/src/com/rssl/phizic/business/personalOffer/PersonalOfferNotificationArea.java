package com.rssl.phizic.business.personalOffer;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferNotificationArea implements PersonalOfferOrderedField
{
	private Long id; // идентификатор
	private String areaName; // область
	private Long orderIndex; // пор€док отображени€

	public PersonalOfferNotificationArea()
	{
	}

	public PersonalOfferNotificationArea(String areaName, Long orderIndex)
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
