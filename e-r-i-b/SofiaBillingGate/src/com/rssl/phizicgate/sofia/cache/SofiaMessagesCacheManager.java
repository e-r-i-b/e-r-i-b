package com.rssl.phizicgate.sofia.cache;

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
public class SofiaMessagesCacheManager extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

	public SofiaMessagesCacheManager(GateFactory factory)
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
					messagesCacheManager = new SofiaMessagesCacheManager(GateSingleton.getFactory());

				cacheManager = messagesCacheManager;
			}
		}
		return cacheManager;
	}

	public Document get(Document message)
	{
		return null;
	}

	public void put(Document request, Document response)
	{
	}
}