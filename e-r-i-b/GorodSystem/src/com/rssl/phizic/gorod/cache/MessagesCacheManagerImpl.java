package com.rssl.phizic.gorod.cache;

import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Document;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Egorova
 * @ created 18.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MessagesCacheManagerImpl extends MessagesCacheManager
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final String RECIPIENT_LIST_Q = "ReqCard";
	private static final String REQ_GLOBAL_Q= "ReqGlobal";

	private Map<String, MessagesCache> caches = new HashMap<String, MessagesCache>();

	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

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

	public MessagesCacheManagerImpl (GateFactory factory)
	{
		super(factory);
		caches.put(RECIPIENT_LIST_Q, new RecipientListQCache());
		caches.put(REQ_GLOBAL_Q, new ReqGlobalQCache());
		fillRemoveMap(caches.values());
	}

	private MessagesCache getCache ( Document request )
	{
		return caches.get(request.getDocumentElement().getNodeName());
	}

	public Document get ( Document request )
	{
		MessagesCache cache = getCache(request);
		if (cache!=null)
		{
			return cache.get(request);
		}
		return null;
	}

	public void put ( Document request, Document responce )
	{
		MessagesCache cache = getCache(request);
		if (cache!=null)
		{
			cache.put(request, responce);
		}
	}


}
