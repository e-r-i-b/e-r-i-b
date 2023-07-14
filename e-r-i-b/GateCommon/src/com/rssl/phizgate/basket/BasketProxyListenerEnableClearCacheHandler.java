package com.rssl.phizgate.basket;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.events.EventHandler;
import net.sf.ehcache.Cache;

/**
 * @author vagin
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 * ���������� ��������� ������������� ������ ���� ��������� ������������ �������� ��������� �� �� "AutoPay" � ������ ������� ��������.
 */
public class BasketProxyListenerEnableClearCacheHandler implements EventHandler<BasketProxyListenerEnableClearCacheEvent>
{
	public void process(BasketProxyListenerEnableClearCacheEvent event) throws Exception
	{
		Cache cache = CacheProvider.getCache("basket-proxy-listener-enable-cache");
		if(cache != null)
			cache.removeAll();
	}
}
