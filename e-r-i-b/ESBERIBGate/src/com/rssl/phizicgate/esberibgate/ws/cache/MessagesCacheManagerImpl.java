package com.rssl.phizicgate.esberibgate.ws.cache;

import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import org.w3c.dom.Document;

/**
 * @ author: filimonova
 * @ created: 28.09.2010
 * @ $Author$
 * @ $Revision$
 * TODO реализовать
 */
public class MessagesCacheManagerImpl extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

	public MessagesCacheManagerImpl (GateFactory factory)
	{
		super(factory);
	}

	public static MessagesCacheManager getInstance ()
	{
		MessagesCacheManager cacheManager = messagesCacheManager;
		if (cacheManager == null)
		{
			synchronized (MANAGER_LOCKER)
			{
				if (messagesCacheManager == null)
					messagesCacheManager = new MessagesCacheManagerImpl(GateSingleton.getFactory());

				cacheManager = messagesCacheManager;
			}
		}
		return cacheManager;
	}

	public Document get(Document message)
	{
		// TODO реализовать
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void put(Document request, Document response)
	{
		// TODO реализовать
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
