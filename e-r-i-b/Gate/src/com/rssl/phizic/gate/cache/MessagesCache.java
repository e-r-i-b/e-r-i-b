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
	 * ��� ����
	 * @return
	 */
	protected abstract String getCacheName();

	/**
	 * �������� ���� �� ���������
	 * @param request
	 * @return
	 */
	public abstract Serializable getKey(Document request);

	/**
	 * ������ ������� �� ������� ���������� ������
	 * @return ������ �������.
	 */
	public abstract List<Class> getCacheClasses();

	/**
	 * �������� ��� �� ����������� �������
	 * @param object ������ ������ �� �������, ����������� getCacheClasses
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
			log.error("������ ��� ��������� ��������� �� ����:", e);
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
			log.error("������ ��� ���������� ��������� � ���:", e);
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
			log.error("������ ��� �������� ��������� �� ���:", e);
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