package com.rssl.auth.csa.wsclient.events;

import com.rssl.phizic.events.EventHandler;
import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;

/**
 * @author basharin
 * @ created 02.02.2013
 * @ $Author$
 * @ $Revision$
 * ������� ��������� ��������� �� ��������� �������� �� ������� �������� ����� � �������.
 */

public class ClearCacheAuthNewsHandler implements EventHandler<ClearCacheAuthNewsEvent>
{
	public void process(ClearCacheAuthNewsEvent event) throws Exception
	{
		// ������ ��� ��� ������ �������� �� �������� ����� ������ � ������
		Cache logonNewsCache = CacheProvider.getCache("news-client-csa-cache");
		if(logonNewsCache != null)
			logonNewsCache.removeAll();

		// ������ ��� ��� ������ �������� �� �������� ���� �������
		Cache allNewsCache = CacheProvider.getCache("list-news-client-csa-cache");
		if(allNewsCache != null)
			allNewsCache.removeAll();

	}
}
