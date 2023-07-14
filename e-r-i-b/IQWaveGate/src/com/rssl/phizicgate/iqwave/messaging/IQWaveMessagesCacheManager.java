package com.rssl.phizicgate.iqwave.messaging;

import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 02.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class IQWaveMessagesCacheManager extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

	public IQWaveMessagesCacheManager(GateFactory factory)
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
					messagesCacheManager = new IQWaveMessagesCacheManager(GateSingleton.getFactory());

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
