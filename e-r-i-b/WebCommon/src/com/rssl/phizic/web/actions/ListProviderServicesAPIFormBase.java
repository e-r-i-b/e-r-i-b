package com.rssl.phizic.web.actions;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма получения списка услуг поставщика
 *
 * @ author: Gololobov
 * @ created: 21.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderServicesAPIFormBase extends ActionFormBase
{
	//приходящий id поставщика
	private Long id;
	//кросблочный идентификатор поставщика
	private String providerGuid;
	//Идентификатор региона
	private Long regionId;
	//Уникальный идентификатор региона
	private String regionGuid;

	//список услуго-поставщиков или поставщико-услуг.
	private List<BillingServiceProvider> serviders = new ArrayList<BillingServiceProvider>();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getProviderGuid()
	{
		return providerGuid;
	}

	public void setProviderGuid(String providerGuid)
	{
		this.providerGuid = providerGuid;
	}

	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
	}

	public String getRegionGuid()
	{
		return regionGuid;
	}

	public void setRegionGuid(String regionGuid)
	{
		this.regionGuid = regionGuid;
	}

	public List<BillingServiceProvider> getServiders()
	{
		return serviders;
	}

	public void setServiders(List<BillingServiceProvider> serviders)
	{
		this.serviders = serviders;
	}
}
