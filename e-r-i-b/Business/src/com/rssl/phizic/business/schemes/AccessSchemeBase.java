package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.services.Service;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kosyakov
 * @ created 13.04.2006
 * @ $Author: koptyaev $
 * @ $Revision: 61844 $
 */
public abstract class AccessSchemeBase implements AccessScheme, Serializable
{
	private Long   id;
	private Long   externalId;
	private String name;
	private String category = AccessCategory.CATEGORY_CLIENT; // по умолчанию, для совместимости
	private List<Service> services = new ArrayList<Service>();
	private boolean CAAdminScheme = false;
	private boolean mailManagement = false;

	/**
	 * Доступно сотрудникам ВСП.
	 */
	private boolean VSPEmployeeScheme = false;

	public AccessSchemeBase(AccessSchemeBase scheme)
	{
		this.id = scheme.getId();
		this.name = scheme.getName();
		this.category = scheme.getCategory();
		services.clear();

		for (Service service : scheme.getServices())
		{
			services.add(new Service(service));
		}
	}

	public AccessSchemeBase()
	{
	}

	public Long getId()
	{
	    return id;
	}

	public void setId(Long id)
	{
	    this.id = id;
	}

	public Long getExternalId()
	{
		return externalId;
	}

	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

	public String getName()
	{
	    return name;
	}

	public void setName(String name)
	{
	    this.name = name;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public List<Service> getServices()
	{
	    return services;
	}

	public void setServices(List<Service> services)
	{
	    this.services = services;
	}

	public String toString()
	{
	    return getName();
	}

	public boolean isCAAdminScheme()
	{
		return CAAdminScheme;
	}

	public void setCAAdminScheme(boolean CAAdminScheme)
	{
		this.CAAdminScheme = CAAdminScheme;
	}

	public boolean isVSPEmployeeScheme()
	{
		return VSPEmployeeScheme;
	}

	public void setVSPEmployeeScheme(boolean VSPEmployeeScheme)
	{
		this.VSPEmployeeScheme = VSPEmployeeScheme;
	}

	public boolean isContainAllTbAccessService()
	{
		//Признак наличия в схеме услуги "Доступ по-умолчанию ко всем ТБ"
		if (CollectionUtils.isNotEmpty(services))
			for (Service service : services)
				if (service.getKey().equalsIgnoreCase("AllTBAccess"))
				{
					return true;
				}

		return false;
	}

	/**
	 * @return есть ли возможность работать с письмами
	 */
	public boolean isMailManagement()
	{
		return mailManagement;
	}

	/**
	 * задать признак возможности работать с письмами
	 * @param mailManagement есть ли возможность работать с письмами
	 */
	public void setMailManagement(boolean mailManagement)
	{
		this.mailManagement = mailManagement;
	}
}
