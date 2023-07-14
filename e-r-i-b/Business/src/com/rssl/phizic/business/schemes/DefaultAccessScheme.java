package com.rssl.phizic.business.schemes;

import com.rssl.phizic.common.types.client.DefaultSchemeType;

import java.io.Serializable;

/**
 * @author gladishev
 * @ created 07.02.2011
 * @ $Author$
 * @ $Revision$
 *
 * Дефолтная схема прав
 */
public class DefaultAccessScheme implements Serializable
{
	private Long id;
	private Long accessSchemeId;
	private String departmentTb;
	private DefaultSchemeType creationType;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getAccessSchemeId()
	{
		return accessSchemeId;
	}

	public void setAccessSchemeId(Long accessSchemeId)
	{
		this.accessSchemeId = accessSchemeId;
	}

	/**
	 * @return ТБ
	 */
	public String getDepartmentTb()
	{
		return departmentTb;
	}

	/**
	 * задать ТБ дефолтной схемы прав
	 * @param departmentTb ТБ
	 */
	public void setDepartmentTb(String departmentTb)
	{
		this.departmentTb = departmentTb;
	}

	public DefaultSchemeType getCreationType()
	{
		return creationType;
	}

	public void setCreationType(DefaultSchemeType creationType)
	{
		this.creationType = creationType;
	}
}
