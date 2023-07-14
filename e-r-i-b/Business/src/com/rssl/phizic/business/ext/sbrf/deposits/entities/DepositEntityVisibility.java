package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.util.List;

/**
 * Видимость подвида вкладного продукта в тербанке
 * @author EgorovaA
 * @ created 13.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityVisibility
{
	private Long id;
	private Long depositType;
	private String name;
	private boolean availableOnline;
	private List<Long> depositSubTypes;
	private List<String> allowedDepartments;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getDepositType()
	{
		return depositType;
	}

	public void setDepositType(Long depositType)
	{
		this.depositType = depositType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isAvailableOnline()
	{
		return availableOnline;
	}

	public void setAvailableOnline(boolean availableOnline)
	{
		this.availableOnline = availableOnline;
	}

	public List<Long> getDepositSubTypes()
	{
		return depositSubTypes;
	}

	public void setDepositSubTypes(List<Long> depositSubTypes)
	{
		this.depositSubTypes = depositSubTypes;
	}

	public List<String> getAllowedDepartments()
	{
		return allowedDepartments;
	}

	public void setAllowedDepartments(List<String> allowedDepartments)
	{
		this.allowedDepartments = allowedDepartments;
	}
}
