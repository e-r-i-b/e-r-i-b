package com.rssl.auth.csa.front.operations.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.auth.csa.front.business.news.NewsCSAService;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author basharin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginViewNewsOperation
{

	private static final NewsCSAService newsCSAService = new NewsCSAService();
	private static final Cache newsCache;

	static
	{
		newsCache = CacheProvider.getCache("one-news-client-csa-cache");
	}

	/**
	 * получение списка 3 последних новостей
	 */
	public News initialize(Long id) throws Exception
	{
		if (id == null)
			return null;

		Element element = null;
		element = newsCache.get(id);

        if (element == null)
		{
			News news = getOneNews(id);
			newsCache.put(new Element(id, news));
			return news;
		}
		return (News) element.getObjectValue();
	}

	private News getOneNews(Long id) throws Exception
	{
		return newsCSAService.getOneNews(id);
	}
}
