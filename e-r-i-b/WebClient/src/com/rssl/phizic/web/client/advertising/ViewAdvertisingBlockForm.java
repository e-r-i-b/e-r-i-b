package com.rssl.phizic.web.client.advertising;

import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 23.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewAdvertisingBlockForm  extends ActionFormBase
{
	private AdvertisingBlock advertisingBlock; //текущий баннер
	private List<Long> bannersList; //список id доступных баннеров в порядке отображения
	private Long currentBannerId; //id текущего баннера

	public AdvertisingBlock getAdvertisingBlock()
	{
		return advertisingBlock;
	}

	public void setAdvertisingBlock(AdvertisingBlock advertisingBlock)
	{
		this.advertisingBlock = advertisingBlock;
	}

	public List<Long> getBannersList()
	{
		return bannersList;
	}

	public void setBannersList(List<Long> bannersList)
	{
		this.bannersList = bannersList;
	}

	public Long getCurrentBannerId()
	{
		return currentBannerId;
	}

	public void setCurrentBannerId(Long currentBannerId)
	{
		this.currentBannerId = currentBannerId;
	}
}
