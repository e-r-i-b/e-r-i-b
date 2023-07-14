package com.rssl.phizgate.basket;

import com.rssl.phizic.events.Event;

/**
 * @author vagin
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 * Эвент необходимости сброса кеша настройки включенности листнера сообщений от АС "AutoPay" в рамках корзины платежей.
 */
public class BasketProxyListenerEnableClearCacheEvent implements Event
{
	public String getStringForLog()
	{
		return "BasketProxyListenerEnableClearCacheEvent";
	}
}
