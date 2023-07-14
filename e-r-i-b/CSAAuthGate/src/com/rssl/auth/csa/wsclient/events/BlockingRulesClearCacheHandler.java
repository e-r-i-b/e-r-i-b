package com.rssl.auth.csa.wsclient.events;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.events.EventHandler;
import net.sf.ehcache.Cache;

/**
 * @author vagin
 * @ created 06.09.2012
 * @ $Author$
 * @ $Revision$
 * ������� ��������� ��������� �� ��������� ������ ���������� ����� ������� �� ��� ����������.
 */
public class BlockingRulesClearCacheHandler implements EventHandler<BlockingRulesClearCacheEvent>
{
	public void process(BlockingRulesClearCacheEvent event) throws Exception
	{
		// ������ ���
		Cache cache = CacheProvider.getCache("global-bloking-rule-csa-cache");
		if(cache != null)
			cache.removeAll();

		Cache cacheNotification = CacheProvider.getCache("bloking-rule-notification-csa-cache");
		if(cacheNotification != null)
			cacheNotification.removeAll();
	}
}
