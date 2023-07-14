package com.rssl.phizicgate.sbrf.ws.cache;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gainanov
 * @ created 06.12.2007
 * @ $Author$
 * @ $Revision$
 */
public class MessagesCacheManagerImpl extends MessagesCacheManager
{
	private static final String ACCOUNT_INFO_Q = "accountInfoDemand_q";
	private static final String ACCOUNT_BALANCE_Q = "accountBalanceDemand_q";
	private static final String BILLING_DEMAND_Q = "billingDemand_q";

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

		caches.put(ACCOUNT_BALANCE_Q, new AccountBalanceDemandQCache());
		caches.put(ACCOUNT_INFO_Q, new AccountInfoDemandQCache());
		caches.put(BILLING_DEMAND_Q, new BillingDemandQCache());

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
