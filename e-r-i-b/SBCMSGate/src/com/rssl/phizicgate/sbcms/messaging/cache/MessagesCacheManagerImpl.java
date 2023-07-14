package com.rssl.phizicgate.sbcms.messaging.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author Egorova
 * @ created 18.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MessagesCacheManagerImpl extends MessagesCacheManager
{
	private static final String BAlANCE_Q = "BALANCE";
	private static final String HISTORY_Q = "HISTORY";

	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager messagesCacheManager = null;

	private Map<String, MessagesCache> caches = new HashMap<String, MessagesCache>();

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

		caches.put(BAlANCE_Q, new BalanceQCache());
		caches.put(HISTORY_Q, new HistoryQCache());

		fillRemoveMap(caches.values());
	}

	private MessagesCache getCache ( Document request )
	{
		try
		{
			String requestType = XmlHelper.getElementValueByPath(request.getDocumentElement(), "/*/PRO_CODE");
			return caches.get(requestType);
		}
		catch (TransformerException e)
		{
			log.error("Ошибка при получении типа сообщения:", e);
			return null;
		}
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
