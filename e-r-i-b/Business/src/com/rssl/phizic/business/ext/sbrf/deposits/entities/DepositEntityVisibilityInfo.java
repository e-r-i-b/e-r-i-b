package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.math.BigDecimal;

/**
 * Сущность для описания подвида вклада (для страницы настройки видимости)
 * @author EgorovaA
 * @ created 19.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityVisibilityInfo
{
	private Long id;
	private Long type;
	private Long group;
	private Long subType;
	private String name;
	private String period;
	private BigDecimal rate;
	private boolean availableOnline;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getType()
	{
		return type;
	}

	public void setType(Long type)
	{
		this.type = type;
	}

	public Long getGroup()
	{
		return group;
	}

	public void setGroup(Long group)
	{
		this.group = group;
	}

	public Long getSubType()
	{
		return subType;
	}

	public void setSubType(Long subType)
	{
		this.subType = subType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public boolean isAvailableOnline()
	{
		return availableOnline;
	}

	public void setAvailableOnline(boolean availableOnline)
	{
		this.availableOnline = availableOnline;
	}
}
