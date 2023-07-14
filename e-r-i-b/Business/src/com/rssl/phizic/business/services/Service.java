package com.rssl.phizic.business.services;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 03.04.2006
 * @ $Author: koptyaev $
 * @ $Revision: 61844 $
 */
public class Service implements Serializable
{
	private Long id;
	private String key;
	private String name;
	private String category;
	private boolean caAdminService = false;

	public Service()
	{

	}

	public Service(Service service)
	{
		this.id = service.getId();
		this.key = service.getKey();
		this.name = service.getName();
		this.category = service.getCategory();
		this.caAdminService = service.isCaAdminService();
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isCaAdminService()
	{
		return caAdminService;
	}

	public void setCaAdminService(boolean caAdminService)
	{
		this.caAdminService = caAdminService;
	}

	public String toString()
	{
		return "[" + key + "] " + name;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Service service = (Service) o;

		if (!key.equals(service.key))
			return false;

		return true;
	}

	public int hashCode()
	{
		return key.hashCode();
	}
}