package com.rssl.phizicgate.asbc.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 02.02.2010
 * @ $Author$
 * @ $Revision$
 */
public class ASBCMessagesCacheManager extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

	public ASBCMessagesCacheManager(GateFactory factory)
	{
		super(factory);
	}

	public static MessagesCacheManager getInstance()
	{
		MessagesCacheManager cacheManager = messagesCacheManager;
		if (cacheManager == null)
		{
			synchronized (MANAGER_LOCKER)
			{
				if (messagesCacheManager == null)
					messagesCacheManager = new ASBCMessagesCacheManager(GateSingleton.getFactory());

				cacheManager = messagesCacheManager;
			}
		}
		return cacheManager;
	}

	public Document get(Document message)
	{
		//TODO при необходимосчти реализовать кеш
		return null;
	}

	public void put(Document request, Document response)
	{
		//TODO при необходимосчти реализовать кеш
	}
}
