package com.rssl.auth.csa.wsclient.events;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;

/**
 * @author basharin
 * @ created 02.02.2013
 * @ $Author$
 * @ $Revision$
 * Хендлер обработки сообщения об изменении новостей на главной странице входа в систему.
 */

public class ClearCacheAuthNewsHandler implements EventHandler<ClearCacheAuthNewsEvent>
{
	public void process(ClearCacheAuthNewsEvent event) throws Exception
	{
		// чистим кэш для списка новостей на странице ввода логина и пароля
		Cache logonNewsCache = CacheProvider.getCache("news-client-csa-cache");
		if(logonNewsCache != null)
			logonNewsCache.removeAll();

		// чистим кэш для списка новостей на странице всех событий
		Cache allNewsCache = CacheProvider.getCache("list-news-client-csa-cache");
		if(allNewsCache != null)
			allNewsCache.removeAll();

	}
}
