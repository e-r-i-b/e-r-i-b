package com.rssl.phizic.business.dictionaries.payment.services.api;

import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lukina
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 * Сущность "услуга", используется для старых версий АПИ
 */

public class PaymentServiceOld
{
	private Long id;
	private Comparable synchKey;
	private String name;
	private PaymentServiceOld parent;
	private boolean popular;
	private Long imageId;
    private String description;
	private boolean system;
	private Long priority;
    private Set<CategoryServiceType> categories  = new HashSet<CategoryServiceType>();
	private boolean visibleInSystem; //отображать услугу в системе или нет. используется при загрузке справочника ЦАС НСИ
	private String defaultImage;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Comparable getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public PaymentServiceOld getParent()
	{
		return parent;
	}

	public void setParent(PaymentServiceOld parent)
	{
		this.parent = parent;
	}

	public boolean isPopular()
	{
		return popular;
	}

	public void setPopular(boolean popular)
	{
		this.popular = popular;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PaymentServiceOld that = (PaymentServiceOld) o;
		if (!id.equals(that.id))
			return false;

		return true;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	public boolean isSystem()
	{
		return system;
	}

	public void setSystem(boolean system)
	{
		this.system = system;
	}

	public Long getPriority()
	{
		return priority;
	}

	public void setPriority(Long priority)
	{
		this.priority = priority;
	}

	public Set<CategoryServiceType> getCategories()
	{
		return categories;
	}

	public void setCategories(Set<CategoryServiceType> categories)
	{
		this.categories = categories;
	}

	public boolean isVisibleInSystem()
	{
		return visibleInSystem;
	}

	public void setVisibleInSystem(boolean visibleInSystem)
	{
		this.visibleInSystem = visibleInSystem;
	}

	public String getDefaultImage()
	{
		return defaultImage;
	}

	public void setDefaultImage(String defaultImage)
	{
		this.defaultImage = defaultImage;
	}
}

