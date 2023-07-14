package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.dictionaries.regions.Region;

import java.io.Serializable;

/**
 * @author akrenev
 * @ created 14.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderRegion implements Serializable
{
	private ServiceProviderBase serviceProvider;
	private Region region;
	private boolean showInPromoBlock;

	public ServiceProviderRegion() {};
	
	public ServiceProviderRegion(ServiceProviderBase serviceProvider, Region region)
	{
		this.serviceProvider = serviceProvider;
		this.region = region;
	}

	public ServiceProviderBase getServiceProvider()
	{
		return serviceProvider;
	}

	public void setServiceProvider(ServiceProviderBase serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}

	public Region getRegion()
	{
		return region;
	}

	public void setRegion(Region region)
	{
		this.region = region;
	}

	public boolean getShowInPromoBlock()
	{
		return showInPromoBlock;
	}

	public void setShowInPromoBlock(boolean showInPromoBlock)
	{
		this.showInPromoBlock = showInPromoBlock;
	}
}
