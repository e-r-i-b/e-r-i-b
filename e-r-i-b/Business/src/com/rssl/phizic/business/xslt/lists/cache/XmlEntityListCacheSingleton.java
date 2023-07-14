package com.rssl.phizic.business.xslt.lists.cache;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.xslt.lists.EntityListDefinition;
import com.rssl.phizic.business.xslt.lists.EntityListsConfig;
import com.rssl.phizic.business.xslt.lists.cache.composers.CacheKeyComposer;
import com.rssl.phizic.business.xslt.lists.cache.composers.SessionCacheKeyComposer;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gladishev
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class XmlEntityListCacheSingleton
{

	public static final String SESSION_LISTS_KEY = "prsn:";
	public static final String STATIC_KEY = "staticKey";
	public static final String DELIMETER_KEY = "/";
	public static final String XML_CALLBACK_CACHE_NAME = "XML_CALLBACK_CACHE";
	//кеш в котором храним классы по которым следует чистить кеш
	public static final String XML_FILL_CLASS_CACHE_NAME = "FILL_CLASS_CACHE";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);

	private static final XmlEntityListCacheSingleton entityListCacheSingleton = new XmlEntityListCacheSingleton();
	private EntityListsConfig entityListsConfig;

	/**
	 * @return инстанс синглтона
	 */
	public static XmlEntityListCacheSingleton getInstance()
	{
		return entityListCacheSingleton;
	}

	private XmlEntityListCacheSingleton()
	{
		entityListsConfig = ConfigFactory.getConfig(EntityListsConfig.class);
		int maxTimeToIdleSeconds = 0;
		int maxTimeToLiveSeconds = 0;
		for (Iterator<EntityListDefinition> itr = entityListsConfig.getListDefinitionsIterator(); itr.hasNext(); )
		{
			EntityListDefinition definition = itr.next();
			int timeToIdleSeconds = definition.getTimeToIdleSeconds();
			int timeToLiveSeconds = definition.getTimeToLiveSeconds();
			if (timeToIdleSeconds > maxTimeToIdleSeconds)
				maxTimeToIdleSeconds = timeToIdleSeconds;
			if (timeToLiveSeconds > maxTimeToLiveSeconds)
				maxTimeToLiveSeconds = timeToLiveSeconds;
		}
		addCache(XmlEntityListCacheSingleton.XML_CALLBACK_CACHE_NAME, 1000, maxTimeToIdleSeconds, maxTimeToLiveSeconds);
		addCache(XmlEntityListCacheSingleton.XML_FILL_CLASS_CACHE_NAME, 1000, maxTimeToIdleSeconds, maxTimeToLiveSeconds);
	}

	/**
	 * Добавляет кеш
	 * @param name имя кеша
	 * @param maxElementsInMemory максимальное количество элементов в памяти
	 * @param timeToIdleSeconds - время жизни записи без обращений
	 * @param timeToLiveSeconds - полное время жизни записи
	 */
	public void addCache(String name, int maxElementsInMemory, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		CacheProvider.addCache(name, maxElementsInMemory, true, false, timeToIdleSeconds, timeToLiveSeconds);
		log.trace("Добавлен кеш для xml-справочника с именем " + name);
	}

	/**
	 * @param name - название кеша
	 * @return кеш xml справочника с именем name
	 */
	public Cache getCache(String name)
	{
		return CacheProvider.getCache(name);
	}

	/**
	 * @return колбаккеш
	 */
	public Cache getCallBackCache()
	{
		return CacheProvider.getCache(XML_CALLBACK_CACHE_NAME);
	}

	/**
	 * Возвращает композер для класса clazz
	 * @param clazz - класс для которого ищем композер
	 * @return композер
	 */
	public CacheKeyComposer getComposer(Class clazz)
	{
		return entityListsConfig.getComposer(clazz);
	}

	/**
	 * Возвращает композер для класса
	 * @param clazz - класс для которого ищем композер
	 * @return композер
	 * @throws BusinessException
	 */
	public SessionCacheKeyComposer getSessionComposer(Class clazz)
	{
		CacheKeyComposer composer = entityListsConfig.getComposer(clazz);
		if (composer != null && composer instanceof SessionCacheKeyComposer)
			return (SessionCacheKeyComposer) composer;

		return null;
	}

	/**
	 * Очистка кеша xml-справочников по объекту object
	 * 1. берем записи вида <название кеша, ключ> привязанные
	 * в колбаккеше к данному объекту и чистим записи
	 * 2 - с помощь сессион-композера получаем сессионые ключи, по которым надо чистить кеш по классу объекта, 
	 * из fillClassCache берем список справочников которые были заполнены по данному классу
	 * @param object - объект по которому очищаются справочники
	 * @param objectClazz - класс по которому ищем композер
	 */
	public void clearCache(Object object, Class objectClazz)
	{
		log.trace("Нить:" + Thread.currentThread().getId() + " Очищаем кеш xml справочников для объекта типа " + objectClazz.getName());
		clearCacheByCallBackKey(object, objectClazz);

		cleareCacheByObjectType(object, objectClazz);
	}

	private void clearCacheByCallBackKey(Object object, Class composerClazz)
	{
		CacheKeyComposer composer = getComposer(composerClazz);
		if (composer == null)
			return;

		Serializable callBackKey = composer.getKey(object);
		if (callBackKey == null)
			return;

		Cache callBackCache = getCallBackCache();
		Element elem = callBackCache.get(callBackKey);
		try
		{
			if (elem != null)
			{
				List<Pair<String, Serializable>> keys = (List<Pair<String, Serializable>>) elem.getObjectValue();
				for (Pair<String, Serializable> key : keys)
				{
					Cache cache = getCache(key.getFirst());
					cache.remove(key.getSecond());
					log.trace("Нить:" + Thread.currentThread().getId() + " Удалено  из кеша справочников " + key.getFirst() + " по ключу  " + key.getSecond());
				}
				callBackCache.remove(callBackKey);
			}
		}
		catch (IllegalStateException e)
		{
			log.error("Нить:" + Thread.currentThread().getId() + "Ошибка при очистке кеша xml справочников по объекту" + object.toString(), e);
		}
	}

	//хотя метод очистки по типу, но в метод передается и сам объект - по объекту определяем сессионные ключи,
	// т.е. получаем владельцев сущности например кредит может быть связан с двумя пользователями,
	//а значит нам необходимо чистить кеш справочников для обоих пользователей
	private void cleareCacheByObjectType(Object object, Class objectClazz)
	{
		List<EntityListDefinition> definitions = entityListsConfig.getListDefinitionsByClassName(objectClazz);
		//сначала проверяем заполняются ли кеши по данному типу
		if (definitions == null)
			return;

		SessionCacheKeyComposer sessionComposer = getSessionComposer(objectClazz);
		String className = objectClazz.getCanonicalName();
		if (sessionComposer == null) //объект не привязан к пользователю => чистим весь кеш
		{
			for (EntityListDefinition definition : definitions)
			{
				Cache cache = getCache(definition.getName());
				if (cache == null) //кэш ещё не создан
					continue;

				cache.removeAll();
				log.trace("Нить:" + Thread.currentThread().getId() + " Удалены все записи из кеша справочника " + definition.getName());
			}
			return;
		}

		//sessionComposer != null => ищем с его помощью всех пользователей (точнее ключи вида prsn:67), для которых необходимо чистить справочники
		List<String> sessionKeys;
		try
		{
			sessionKeys = sessionComposer.getSessionKeys(object);

			if (sessionKeys == null || sessionKeys.isEmpty())
			{
				log.error("Не удалось получить ключи для очистки сессионных справочников");
				return;
			}
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return;
		}

		//выкидываем из sessionKeys ключи по которым кеши справочников по классу objectClazz не заполнялись
		Map<String, List<String>> needClearCaches = getKeysNeedClearByType(sessionKeys, className);

		if (needClearCaches.isEmpty())
			return;

		Cache fillClassCache = getFillClassCache();
		for (String key : needClearCaches.keySet())
		{
			for (String defiitionName : needClearCaches.get(key))
			{
				EntityListDefinition definition = entityListsConfig.getListDefinition(defiitionName);
				boolean parametrizedList = definition.getCacheParameters().size() > 0;

				clearSessionEntityListCache(getCache(definition.getName()), parametrizedList, key, defiitionName);
			}

			//удаляем из fillClassCache запись с ключем fillCacheKey - ей там больше не место
			fillClassCache.remove(className + DELIMETER_KEY + key);
		}
	}

	/**
	 * Метод возвращает из списка sessionKeys ключи по которым необходимо чистить справочники по классу objectClazz
	 * а также соответствующие им списки названий спрвочников для очистки
	 * @param sessionKeys список ключей
	 * @param className - название класса
	 * @return мап сессионный ключ, список названий справочников для очистки
	 */
	private Map<String, List<String>> getKeysNeedClearByType(List<String> sessionKeys, String className)
	{
		Cache fillClassCache = getFillClassCache();
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		for (String sessionKey : sessionKeys)
		{
			String fillCacheKey = className + DELIMETER_KEY + sessionKey;
			Element element = fillClassCache.get(fillCacheKey);
			if (element != null)
			{
				result.put(sessionKey, (List<String>) element.getObjectValue());
			}
		}
		return result;
	}

	/**
	 * @return кеш для хранения значений неоходимости очистки кеша по классам
	 */
	public Cache getFillClassCache()
	{
		return getCache(XML_FILL_CLASS_CACHE_NAME);
	}

	/**
	 * Очистка кеша сессионного справочника
	 * необходимо очистить только записи текущего клиента
	 * - если список параметризованный то удаляем записи с ключами начинающимися
	 * на "prsn:XX" (записей может быть несколько)
	 * @param definitionName - название справочника
	 * @param sessionKey - ключ, по которому удаляем записи
	 */
	private void clearSessionEntityListCache(Cache cache, boolean parametrizedList, String sessionKey, String definitionName)
	{
		if (parametrizedList)
		{
			List<String> keys = cache.getKeys();
			for (String key : keys)
			{
				if (key.startsWith(sessionKey))
					cache.remove(key);
			}
			log.trace("Нить:" + Thread.currentThread().getId() + " Удалено из кеша справочника " + definitionName + " по ключу, начинающего с  " + sessionKey);
		}
		else //список не параметризованный - запись только одна - ключ = "prsn:<id клиента>"
		{
			cache.remove(sessionKey);
			log.trace("Нить:" + Thread.currentThread().getId() + " Удалено из кеша справочника " + definitionName + " по ключу " + sessionKey);
		}
	}
}
