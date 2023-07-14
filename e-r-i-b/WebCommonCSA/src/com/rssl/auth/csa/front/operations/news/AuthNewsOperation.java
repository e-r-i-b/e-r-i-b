package com.rssl.auth.csa.front.operations.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.auth.csa.front.business.news.NewsCSAService;
import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.util.List;

/**
 * @author basharin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class AuthNewsOperation
{
	private static final NewsCSAService newsCSAService = new NewsCSAService();
	private static final String cacheKey       = "newsListCSA";
	private static Cache newsListCache;

	static
	{
		newsListCache  = CacheProvider.getCache("news-client-csa-cache");
	}

	/**
	 * получение списка 3 последних новостей
	 */
	public List<News> initialize() throws Exception
	{
		String regionTb = RegionHelper.getRegionTB();
		String key = cacheKey.concat(regionTb == null ? "" : regionTb);
		Element element = newsListCache.get(key);
        if (element == null)
		{
			List<News> listNews = getNewsList(regionTb);
			newsListCache.put(new Element(key, listNews));
			return listNews; 
		}
		return (List) element.getObjectValue();
	}

	private List<News> getNewsList(String regionTb) throws Exception
	{
		return newsCSAService.getAuthNews(regionTb);
	}


}
