package com.rssl.phizic.business.commission;

/**
 * Тип комиссии. На наго делать ссылку из платежей и заявок.
 * @author Evgrafov
 * @ created 10.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public final class CommissionType
{
	private Long id;
	private String key;
	private String name;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}