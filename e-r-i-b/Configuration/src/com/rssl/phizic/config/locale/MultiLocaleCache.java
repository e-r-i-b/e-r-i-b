package com.rssl.phizic.config.locale;

import com.rssl.phizic.cache.CacheProvider;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author mihaylov
 * @ created 19.10.14
 * @ $Author$
 * @ $Revision$
 *
 * ������������� ���. ������ ������ � ������ ������� ������ �� ���������.
 */
public class MultiLocaleCache
{
	private static final String DELIMITER = "^";
	private Cache cache;

	private MultiLocaleCache(Cache cache)
	{
		this.cache = cache;
	}

	/**
	 * �������� ������������� ���
	 * @param cacheName - ��� ����
	 * @return ������������� ���
	 */
	public static MultiLocaleCache getCache(String cacheName)
	{
		Cache cache = CacheProvider.getCache(cacheName);
		return new MultiLocaleCache(cache);
	}

	/**
	 * �������� ������ �� ����
	 * @param key ���� ��� ��������� �������
	 * @return ����� �� ����
	 */
	public Element get(String key)
	{
		return cache.get(getLocaledKey(key));
	}

	/**
	 * ��������� ������ � ���
	 * @param cacheElement - ������ ��� ��������� � ���
	 */
	public void put(Element cacheElement)
	{
		String localedKey = getLocaledKey((String)cacheElement.getKey());
		Element localedElement = new Element(localedKey,cacheElement.getValue());
		cache.put(localedElement);
	}

	private String getLocaledKey(String key)
	{
		return key + DELIMITER + MultiLocaleContext.getLocaleId();
	}


}
