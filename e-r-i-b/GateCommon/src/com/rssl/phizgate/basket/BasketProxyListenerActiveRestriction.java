package com.rssl.phizgate.basket;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author vagin
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 * Проверка доступности функционала слушателя очередей от АС "AutoPay" корзины платежей.
 * Настройка: Настройки-->Ограничения на операции-->«Обмен инфорамцией по счетами в корзине платеже».
 */
public class BasketProxyListenerActiveRestriction
{
	private static final String GLOBAL_ENABLE_KEY = "GLOBAL_BASKET_PROXY_LISTENER_ENABLE_STATE";
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";
	private static final String APPLICATION_PREFIX= "phiz-basket-proxy-listener";

	/**
	 * Включен ли функционал.
	 * @return true/false - включен/выключен.
	 */
	public static boolean check()
	{
		Cache cache = CacheProvider.getCache("basket-proxy-listener-enable-cache");
		Element element = cache.get(GLOBAL_ENABLE_KEY);
		if(element != null)
			return (Boolean) element.getValue();

		Property enableProperty =  DbPropertyService.findProperty("global.basket.proxy.listener.enable.state", APPLICATION_PREFIX, OFFLINE_DOC_DB_INSTANCE);
		boolean enabled = enableProperty != null ? Boolean.valueOf(enableProperty.getValue()) : false;
		cache.put(new Element(GLOBAL_ENABLE_KEY, enabled));
		return enabled;
	}
}
