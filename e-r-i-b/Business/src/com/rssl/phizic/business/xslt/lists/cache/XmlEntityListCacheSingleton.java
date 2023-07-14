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
	//��� � ������� ������ ������ �� ������� ������� ������� ���
	public static final String XML_FILL_CLASS_CACHE_NAME = "FILL_CLASS_CACHE";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);

	private static final XmlEntityListCacheSingleton entityListCacheSingleton = new XmlEntityListCacheSingleton();
	private EntityListsConfig entityListsConfig;

	/**
	 * @return ������� ���������
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
	 * ��������� ���
	 * @param name ��� ����
	 * @param maxElementsInMemory ������������ ���������� ��������� � ������
	 * @param timeToIdleSeconds - ����� ����� ������ ��� ���������
	 * @param timeToLiveSeconds - ������ ����� ����� ������
	 */
	public void addCache(String name, int maxElementsInMemory, int timeToIdleSeconds, int timeToLiveSeconds)
	{
		CacheProvider.addCache(name, maxElementsInMemory, true, false, timeToIdleSeconds, timeToLiveSeconds);
		log.trace("�������� ��� ��� xml-����������� � ������ " + name);
	}

	/**
	 * @param name - �������� ����
	 * @return ��� xml ����������� � ������ name
	 */
	public Cache getCache(String name)
	{
		return CacheProvider.getCache(name);
	}

	/**
	 * @return ���������
	 */
	public Cache getCallBackCache()
	{
		return CacheProvider.getCache(XML_CALLBACK_CACHE_NAME);
	}

	/**
	 * ���������� �������� ��� ������ clazz
	 * @param clazz - ����� ��� �������� ���� ��������
	 * @return ��������
	 */
	public CacheKeyComposer getComposer(Class clazz)
	{
		return entityListsConfig.getComposer(clazz);
	}

	/**
	 * ���������� �������� ��� ������
	 * @param clazz - ����� ��� �������� ���� ��������
	 * @return ��������
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
	 * ������� ���� xml-������������ �� ������� object
	 * 1. ����� ������ ���� <�������� ����, ����> �����������
	 * � ���������� � ������� ������� � ������ ������
	 * 2 - � ������ �������-��������� �������� ��������� �����, �� ������� ���� ������� ��� �� ������ �������, 
	 * �� fillClassCache ����� ������ ������������ ������� ���� ��������� �� ������� ������
	 * @param object - ������ �� �������� ��������� �����������
	 * @param objectClazz - ����� �� �������� ���� ��������
	 */
	public void clearCache(Object object, Class objectClazz)
	{
		log.trace("����:" + Thread.currentThread().getId() + " ������� ��� xml ������������ ��� ������� ���� " + objectClazz.getName());
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
					log.trace("����:" + Thread.currentThread().getId() + " �������  �� ���� ������������ " + key.getFirst() + " �� �����  " + key.getSecond());
				}
				callBackCache.remove(callBackKey);
			}
		}
		catch (IllegalStateException e)
		{
			log.error("����:" + Thread.currentThread().getId() + "������ ��� ������� ���� xml ������������ �� �������" + object.toString(), e);
		}
	}

	//���� ����� ������� �� ����, �� � ����� ���������� � ��� ������ - �� ������� ���������� ���������� �����,
	// �.�. �������� ���������� �������� �������� ������ ����� ���� ������ � ����� ��������������,
	//� ������ ��� ���������� ������� ��� ������������ ��� ����� �������������
	private void cleareCacheByObjectType(Object object, Class objectClazz)
	{
		List<EntityListDefinition> definitions = entityListsConfig.getListDefinitionsByClassName(objectClazz);
		//������� ��������� ����������� �� ���� �� ������� ����
		if (definitions == null)
			return;

		SessionCacheKeyComposer sessionComposer = getSessionComposer(objectClazz);
		String className = objectClazz.getCanonicalName();
		if (sessionComposer == null) //������ �� �������� � ������������ => ������ ���� ���
		{
			for (EntityListDefinition definition : definitions)
			{
				Cache cache = getCache(definition.getName());
				if (cache == null) //��� ��� �� ������
					continue;

				cache.removeAll();
				log.trace("����:" + Thread.currentThread().getId() + " ������� ��� ������ �� ���� ����������� " + definition.getName());
			}
			return;
		}

		//sessionComposer != null => ���� � ��� ������� ���� ������������� (������ ����� ���� prsn:67), ��� ������� ���������� ������� �����������
		List<String> sessionKeys;
		try
		{
			sessionKeys = sessionComposer.getSessionKeys(object);

			if (sessionKeys == null || sessionKeys.isEmpty())
			{
				log.error("�� ������� �������� ����� ��� ������� ���������� ������������");
				return;
			}
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
			return;
		}

		//���������� �� sessionKeys ����� �� ������� ���� ������������ �� ������ objectClazz �� �����������
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

			//������� �� fillClassCache ������ � ������ fillCacheKey - �� ��� ������ �� �����
			fillClassCache.remove(className + DELIMETER_KEY + key);
		}
	}

	/**
	 * ����� ���������� �� ������ sessionKeys ����� �� ������� ���������� ������� ����������� �� ������ objectClazz
	 * � ����� ��������������� �� ������ �������� ����������� ��� �������
	 * @param sessionKeys ������ ������
	 * @param className - �������� ������
	 * @return ��� ���������� ����, ������ �������� ������������ ��� �������
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
	 * @return ��� ��� �������� �������� ������������ ������� ���� �� �������
	 */
	public Cache getFillClassCache()
	{
		return getCache(XML_FILL_CLASS_CACHE_NAME);
	}

	/**
	 * ������� ���� ����������� �����������
	 * ���������� �������� ������ ������ �������� �������
	 * - ���� ������ ����������������� �� ������� ������ � ������� �������������
	 * �� "prsn:XX" (������� ����� ���� ���������)
	 * @param definitionName - �������� �����������
	 * @param sessionKey - ����, �� �������� ������� ������
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
			log.trace("����:" + Thread.currentThread().getId() + " ������� �� ���� ����������� " + definitionName + " �� �����, ����������� �  " + sessionKey);
		}
		else //������ �� ����������������� - ������ ������ ���� - ���� = "prsn:<id �������>"
		{
			cache.remove(sessionKey);
			log.trace("����:" + Thread.currentThread().getId() + " ������� �� ���� ����������� " + definitionName + " �� ����� " + sessionKey);
		}
	}
}
