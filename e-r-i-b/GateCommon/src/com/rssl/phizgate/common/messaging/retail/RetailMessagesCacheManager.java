package com.rssl.phizgate.common.messaging.retail;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.MessagesCache;
import com.rssl.phizic.gate.cache.MessagesCacheManager;
import com.rssl.phizic.gate.messaging.configuration.MessageInfo;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import com.rssl.phizic.utils.ClassHelper;
import org.w3c.dom.Document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 25.11.2009
 * @ $Author$
 * @ $Revision$
 */

public class RetailMessagesCacheManager extends MessagesCacheManager
{
	private static final Object MANAGER_LOCKER = new Object();
	private static volatile MessagesCacheManager instance = null;
	private final Map<String, MessagesCache> caches = new HashMap<String, MessagesCache>();

	public static MessagesCacheManager getInstance (MessagingConfig messageConfig)
	{
		MessagesCacheManager localInstance = instance;
		if (localInstance == null)
		{
			synchronized (MANAGER_LOCKER)
			{
				if (instance == null)
					instance = new RetailMessagesCacheManager(GateSingleton.getFactory(), messageConfig);
				
				localInstance = instance;
			}
		}
		return localInstance;
	}

	public RetailMessagesCacheManager(GateFactory factory, MessagingConfig messageConfig)
	{
		super(factory);
		try
		{
			Collection<MessageInfo> infos = messageConfig.getAllMessageInfos();
			for (MessageInfo info : infos)
			{
				String className = info.getCacheClassName();
				if(className!=null)
				{
					Class classManager = ClassHelper.loadClass(className);
					MessagesCache cache = (MessagesCache)classManager.getConstructor().newInstance();
					caches.put(info.getName(), cache);
				}

			}

			fillRemoveMap(caches.values());
		}
		catch ( NoSuchMethodException ex)
		{
			throw new RuntimeException("Ошибка при инициализации кеша. В классе кеша должен быть конструктор без аргументов",ex);
		}
		catch ( ClassNotFoundException ex)
		{
			throw new RuntimeException("Ошибка при инициализации кеша. Создайте ненайденный класс или удалите из retail-messaging.cfg.xml",ex);
		}
		catch ( Exception ex)
		{
			throw new RuntimeException("Ошибка при создании класса кеша",ex);
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

	private MessagesCache getCache ( Document request )
	{
		return caches.get(request.getDocumentElement().getNodeName());
	}

	public void put ( Document request, Document responce )
	{
		MessagesCache cache = getCache(request);
		if (cache != null)
		{
			cache.put(request, responce);
		}
	}
}
