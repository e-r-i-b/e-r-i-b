package com.rssl.phizic.gate.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import org.w3c.dom.Document;

/**
 * @author krenev
 * @ created 15.02.2011
 * @ $Author$
 * @ $Revision$
 * Пустой менеджер кеша
 * Используется для шлюзов, ненуждающихся в кеше
 */
public class EmptyMessagesCacheManager extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;


	public EmptyMessagesCacheManager(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Получить документ из кеша, если он там есть
	 * @param message
	 * @return
	 */
	public Document get(Document message)
	{
		return null;
	}

	/**
	 * Записать в кеш запрос и ответ.
	 * @param request
	 * @param response
	 */
	public void put(Document request, Document response){}


	/**
	 * @return инстанс менеджера кеша.
	 */
	public static MessagesCacheManager getInstance()
	{
		MessagesCacheManager cacheManager = messagesCacheManager;
		if (cacheManager == null)
		{
			synchronized (MANAGER_LOCKER)
			{
				if (messagesCacheManager == null)
					messagesCacheManager = new EmptyMessagesCacheManager(GateSingleton.getFactory());

				cacheManager = messagesCacheManager;
			}
		}
		return cacheManager;
	}
}
