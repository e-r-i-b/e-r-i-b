package com.rssl.phizic.business.dictionaries.payment.services;

import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 01.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentService extends DictionaryRecordBase implements DictionaryRecord, MultiBlockDictionaryRecord
{
	private Long id;
	private Comparable synchKey;
	private String name;
	private boolean popular;
	private Long imageId;
    private String description;
	private boolean system;
	private Long priority;
	private boolean visibleInSystem; //отображать услугу в системе или нет. используется при загрузке справочника ЦАС НСИ
	private String defaultImage;
	private boolean isCategory;//является ли услуга категорией(нужно ли отображать услугу на странице ПиП)
	private boolean showInSystem;//показывать услугу в каталоге
	private boolean showInMApi; //показывать услугу в каталоге в Api
	private boolean showInAtmApi; //показывать услугу в каталоге в atmApi
	private boolean showInSocialApi; //показывать услугу в каталоге в socialApi
	private List<PaymentService> parentServices = new ArrayList<PaymentService>();

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

		PaymentService that = (PaymentService) o;
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

	public boolean getCategory()
	{
		return isCategory;
	}

	public void setCategory(boolean category)
	{
		this.isCategory = category;
	}

	public boolean getShowInSystem()
	{
		return showInSystem;
	}

	public void setShowInSystem(boolean showInSystem)
	{
		this.showInSystem = showInSystem;
	}

	public boolean getShowInMApi()
	{
		return showInMApi;
	}

	public void setShowInMApi(boolean showInMApi)
	{
		this.showInMApi = showInMApi;
	}

	public boolean getShowInAtmApi()
	{
		return showInAtmApi;
	}

	public void setShowInAtmApi(boolean showInAtmApi)
	{
		this.showInAtmApi = showInAtmApi;
	}

	public List<PaymentService> getParentServices()
	{
		return parentServices;
	}

	public void setParentServices(List<PaymentService> parentServices)
	{
		this.parentServices = parentServices;
	}

	public String getMultiBlockRecordId()
	{
		return synchKey.toString();
	}

    public boolean getShowInSocialApi()
    {
        return showInSocialApi;
    }

    public void setShowInSocialApi(boolean showInSocialApi)
    {
        this.showInSocialApi = showInSocialApi;
    }
}
