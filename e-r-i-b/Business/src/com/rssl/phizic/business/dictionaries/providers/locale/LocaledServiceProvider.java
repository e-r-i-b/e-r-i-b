package com.rssl.phizic.business.dictionaries.providers.locale;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import org.apache.commons.collections.CollectionUtils;

import java.util.Set;

/**
 * @author mihaylov
 * @ created 09.02.15
 * @ ${Author}
 * @ ${Revision}
 */
public class LocaledServiceProvider extends BillingServiceProvider
{
	private Set<ServiceProviderResources> resources;

	@Override
	public String getName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getName();
		return super.getName();
	}

	@Override
	public String getLegalName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getLegalName();
		return super.getLegalName();
	}

	@Override
	public String getAlias()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getAlias();
		return super.getAlias();
	}

	@Override
	public String getBankName()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getBankName();
		return super.getBankName();
	}

	@Override
	public String getDescription()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getDescription();
		return super.getDescription();
	}

	@Override
	public String getTipOfProvider()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getTipOfProvider();
		return super.getTipOfProvider();
	}

	@Override
	public String getCommissionMessage()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getCommissionMessage();
		return super.getCommissionMessage();
	}

	@Override
	public String getNameOnBill()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getNameOnBill();
		return super.getNameOnBill();
	}

	@Override public String getNameService()
	{
		if(CollectionUtils.isNotEmpty(resources))
			return resources.iterator().next().getNameService();
		return super.getNameService();
	}
}
