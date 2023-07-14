package com.rssl.phizic.gate.cache;

import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Collections;

/**
 * @author Gainanov
 * @ created 06.12.2007
 * @ $Author$
 * @ $Revision$                                                                                                                       
 */
public abstract class MessagesCache
{
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * имя кеша
	 * @return
	 */
	protected abstract String getCacheName();

	/**
	 * получить ключ из документа
	 * @param request
	 * @return
	 */
	public abstract Serializable getKey(Document request);

	/**
	 * список классов по которым кешируются данные
	 * @return список классов.
	 */
	public abstract List<Class> getCacheClasses();

	/**
	 * очистить кеш по переданному объекту
	 * @param object объект одного из классов, возращаемых getCacheClasses
	 */
	public abstract void clear(Object object) throws GateException, GateLogicException;

	private Cache getCache() throws CacheException
	{
		return CacheProvider.getCache(getCacheName());
	}

	public Document get(Document request)
	{
		try
		{
			Cache cache = getCache();
			if (cache != null)
			{
				Serializable key = getKey(request);
				if (key != null)
				{
					Element element = cache.get(getKey(request));
					if (element != null)
					{
						return (Document) element.getValue();
					}
				}
			}
		}
		catch (CacheException e)
		{
			log.error("Ошибка при получении сообщения из кэша:", e);
		}
		return null;
	}

	public void put(Document request, Document responce)
	{
		try
		{
			Cache cache = getCache();
			if (cache != null)
			{
				Serializable key = getKey(request);
				if (key != null)
					cache.put(new Element(key, responce));
			}
		}
		catch (CacheException e)
		{
			log.error("Ошибка при сохранении сообщения в кэш:", e);
		}
	}

	public boolean remove(Serializable key)
	{
		try
		{
			Cache cache = getCache();
			if (cache != null)
			{
				return cache.remove(key);
			}
		}
		catch (CacheException e)
		{
			log.error("Ошибка при удалении сообщения из кэш:", e);
		}
		return false;
	}

	protected List<Object> getAllKeys()
	{
		Cache cache = getCache();
		if (cache != null)
		{
			return cache.getKeys();
		}
		return Collections.emptyList();
	}
}