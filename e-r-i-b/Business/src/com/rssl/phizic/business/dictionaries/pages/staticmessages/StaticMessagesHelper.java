package com.rssl.phizic.business.dictionaries.pages.staticmessages;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PropertyConfig;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author gladishev
 * @ created 28.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class StaticMessagesHelper
{
	private static final StaticMessagesService service = new StaticMessagesService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final Cache messagesCache;

	static{
		messagesCache = CacheProvider.getCache("static-messages-cache");
	}

	/**
	 * Метод поиска текста сообщения по ключу
	 * @param key ключ для поиска
	 * @return текст
	 */
	public static String findTextByKey(String key)
	{
		try
		{
			Element element = messagesCache.get(key);
			if(element == null)
			{
				StaticMessage msg = service.findByKey(key);
				String message;
				if (msg == null)
					message = (ConfigFactory.getConfig(PropertyConfig.class).getProperty(key));
				else
					message = msg.getText();
				element = new Element(key,message);
				messagesCache.put(element);
			}
			return (String) element.getObjectValue();
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении сообщения с ключём " + key, e);
			return "";
		}
	}
}
