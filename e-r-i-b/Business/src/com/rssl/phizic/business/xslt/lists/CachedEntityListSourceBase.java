package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.xslt.lists.cache.CachedEntityListSource;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.business.xslt.lists.cache.composers.CacheKeyComposer;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import static com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton.*;

/**
 * ����� ����������� ����������� xml ������������
 * @author gladishev
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class CachedEntityListSourceBase implements CachedEntityListSource
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Log cacheLog = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);
	protected static final String FILTER_CLASS_NAME_PARAMETER = "filter";
	protected static final String COMPARATOR_CLASS_NAME_PARAMETER = "comparator";

	private EntityListDefinition definition;

	protected CachedEntityListSourceBase(EntityListDefinition definition)
	{
		this.definition = definition;
	}

	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		if (definition.getScope() == EntityListDefinition.SCOPE_REQUEST)
			return new StreamSource(new StringReader(buildEntityList(params).getFirst()));
		return new StreamSource(new StringReader(buildEntityListFromCache(params)));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			if (definition.getScope() == EntityListDefinition.SCOPE_REQUEST)
				return XmlHelper.parse(buildEntityList(params).getFirst());
			return XmlHelper.parse(buildEntityListFromCache(params));
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ����������� �����������
	 */
	public EntityListDefinition getListDefinition()
	{
		return definition;
	}

	/**
	 * ������������ ���������� � ���������� ������� � ��������� ������ buildEntityList
	 * @param xml - ���������� xml �����������
	 * @param objects - ������ �������� ��� ������� ���������� ������� ����� � ������ ����� � ����������
	 * @return ���� <xml, ������ ��������>
	 */
	protected Pair<String, List<Object>> convertToReturnValue(String xml, Object... objects)
	{
		if (objects == null)
			return convertToReturnValue(xml);

		List<Object> objectsList = new ArrayList<Object>();

		for (Object obj : objects)
			objectsList.add(obj);

		return new Pair<String, List<Object>>(xml, objectsList);
	}

	protected Pair<String, List<Object>> convertToReturnValue(String xml)
	{
		return new Pair<String, List<Object>>(xml, null);
	}

	private String buildEntityListFromCache(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		String cacheName = definition.getName();
		String key = buildKey(definition.getScope(), definition.getCacheParameters(), params);
		cacheLog.trace("����:"+Thread.currentThread().getId() + " ��������� xml ���������� " + cacheName + " � ������ " + key);
		XmlEntityListCacheSingleton cacheSingleton = XmlEntityListCacheSingleton.getInstance();
		Cache callBackCache = cacheSingleton.getCallBackCache();
		Cache cache = cacheSingleton.getCache(cacheName);
		Element element = cache.get(key);
		if (element != null)
		{
			Pair<String, List<Serializable>> value = (Pair<String, List<Serializable>>) element.getObjectValue();

			//��������� ������ ���������� ���� �� ������� ������ ������ ���� cacheName, � ������� ���������
			if (value.getSecond() != null)
			{
				for (Serializable callBackKey : value.getSecond())
					callBackCache.get(callBackKey);
			}
			cacheLog.trace("����:"+Thread.currentThread().getId() + " ��� ����������� " + cacheName + " � ������ " + key + " ������� �������� � ����");
			return value.getFirst();
		}

		Pair<String, List<Object>> result = buildEntityList(params);

		List<Serializable> callBackKeys = null;
		List<Class> calbackDependences = definition.getCalbackDependences();
		if (calbackDependences.size() > 0)
		{
			//��� ������� ������� �� ������ (result.getSecond()) ������ ������ � ����������,
			// � �������� ������ - ������ �� ��������� ������ ���� ������� �����������
			List<Object> cachedObjects = result.getSecond();
			if (cachedObjects == null || cachedObjects.size()==0)
			{
				cacheLog.error("����:"+Thread.currentThread().getId() +
						" ����� buildEntityList �� ������ ������� ��� �����������. ��������� �� �������� � ���");
				//��������� �� ��������
				return result.getFirst();
			}
			else
			{
				callBackKeys = new ArrayList<Serializable>();
				int index = 0;
				for (Object object : cachedObjects)
				{
					Class classForComposer = calbackDependences.get(index);
					if (!classForComposer.isAssignableFrom(object.getClass()))
						throw new BusinessException("������ ��� ��������� ��������� ��� ������� ������ " +
									object.getClass() + ", ���������� " + definition.getName());

					CacheKeyComposer composer = cacheSingleton.getComposer(classForComposer);
					if (composer == null)
					{
						throw new BusinessException("�� ������ �������� ��� ������ " + classForComposer);
					}

					Serializable callbackKey = composer.getKey(object);
					fillCallBackCache(callBackCache, cacheName, key, callbackKey);
					callBackKeys.add(callbackKey);
					index++;
				}
			}
		}
		//� ��� ������ ���� <����������� xml, ������ ������-������ ��������� � ������ �����
		// (��� ���������� ������� ��� ��������� xml �� ����)>
		cache.put(new Element(key, new Pair<String, List<Serializable>>(result.getFirst(), callBackKeys)));
		cacheLog.trace("����:"+Thread.currentThread().getId() + " ��������� ���������� " + cacheName + " � ������ " + key + ", ��������� � ���");

		addFillClassCacheValues(cacheSingleton);

		return result.getFirst();
	}

	//� fillClassCache ������ ������ ���� ����=className/prsn:XX  value= ������ ������������, �� ������� ���������� ������� ���
	private void addFillClassCacheValues(XmlEntityListCacheSingleton cacheSingleton)
	{
		Cache fillClassCache = cacheSingleton.getFillClassCache();
		for (Class aclass : definition.getCacheDependences())
		{
			//��������� ������ ��� ���������� �������� (�.�. ��������� � ������������� - �����, �����...)
			if (cacheSingleton.getSessionComposer(aclass) == null)
				continue;

			StringBuilder fillCacheKey = new StringBuilder(aclass.getCanonicalName());
			ApplicationConfig applicationConfig = ApplicationConfig.getIt();
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			fillCacheKey.append(DELIMETER_KEY);
			if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
				fillCacheKey.append(Application.PhizIA.name()).append(DELIMETER_KEY);
			fillCacheKey.append(SESSION_LISTS_KEY);
			fillCacheKey.append(personData.createDocumentOwner().getSynchKey());

			String fillCacheKeyString = fillCacheKey.toString();
			Element container = fillClassCache.get(fillCacheKeyString);
			List<String> list;
			if (container == null)
			{
				list = new ArrayList<String>(1);
			}
			else
			{
				list = (List<String>)container.getObjectValue();
				fillClassCache.remove(fillCacheKeyString);
			}

			list.add(definition.getName());
			fillClassCache.put(new Element(fillCacheKeyString, list));
		}
	}

	private void fillCallBackCache(Cache callBackCache, String cacheName, Serializable key, Serializable callBackKey)
	{
		Element container = callBackCache.get(callBackKey);
		List<Pair<String,Serializable>> list;
		if(container == null)
		{
			list = new ArrayList<Pair<String,Serializable>>(1);
		}
		else
		{
			list = (List<Pair<String,Serializable>>)container.getObjectValue();
			callBackCache.remove(callBackKey);
		}

		list.add(new Pair<String,Serializable>(cacheName,key));
		callBackCache.put(new Element(callBackKey, list));

		StringBuilder builder = new StringBuilder();
		builder.append("����:").append(Thread.currentThread().getId());
		builder.append(", � ��������� ��������� ������ � ������ ").append(callBackKey);
		builder.append(", ��������=<").append(cacheName);
		builder.append(", ").append(key).append(">");
		cacheLog.trace(builder.toString());
	}

	/**
	 * �������� ���� ������ � ����
	 * ���� scope = session �� ������ ������ ����� ����� ������������� ������� (���� prsn:77)
	 * ����� ����� ��������� ��������� (���� ����) � �� ��������, �� ������� �������� ������
	 * ���� scope = application � ��������� � ������ �� ������������ �� ������ � ���� ����� ������ ����
	 * ������� ���� ���������� �����������
	 * @param listScope - ������� ����� ����
	 * @param cacheParameters - �������� ����������, �� ������� �������� ������
	 * @param parameters - ������ ���������� ���������� � �����
	 * @return ����
	 */
	private String buildKey(int listScope, List<String> cacheParameters, Map<String, String> parameters)
	{
		if (listScope == EntityListDefinition.SCOPE_APPLICATION && CollectionUtils.isEmpty(cacheParameters))
			return STATIC_KEY;

		StringBuilder key = new StringBuilder();
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
			key.append(Application.PhizIA.name()).append(DELIMETER_KEY);

		if (listScope == EntityListDefinition.SCOPE_SESSION)
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			key.append(SESSION_LISTS_KEY).append(personData.createDocumentOwner().getSynchKey());
		}

		if (CollectionUtils.isNotEmpty(cacheParameters))
		{
			for (String parameter : cacheParameters)
			{
				String paramValue = parameters.get(parameter);
				if (!StringHelper.isEmpty(paramValue))
				{
					if (key.length()>0)
						key.append(DELIMETER_KEY);

					key.append(parameter);
					key.append(":");
					key.append(paramValue);
				}
			}
		}

		return key.toString();
	}
}
