package com.rssl.phizic.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author khudyakov
 * @ created 20.01.14
 * @ $Author$
 * @ $Revision$
 */
public class CacheHelper
{
	/**
	 * �������� �������� � ���
	 * @param cache ���
	 * @param key ����, ��� ������� ����� �������� ����������� ��������
	 * @param value ����������� ��������
	 * @param <T> ���������� �������
	 * @return ����������� ��������
	 */
	public static <T> T put2cache(Cache cache, Object key, T value)
	{
		if (value == null)
		{
			return null;
		}

		cache.put(new Element(key, value));
		return value;
	}

	/**
	 * �������� ���������� �������� �� ����, ��� ���������� � ���� �� ���������������� �� ���������
	 * @param cache ���
	 * @param key ����
	 * @param action ����� ��������� �� ���������
	 * @param <T> ���������� �������
	 * @return ��������
	 * @throws Exception
	 */
	public static <T> T getCachedEntity(Cache cache, Object key, CacheAction<T> action) throws Exception
	{
		Element element = cache.get(key);
		if (element == null)
		{
			return put2cache(cache, key, action.getEntity());
		}

		//noinspection unchecked
		return (T) element.getObjectValue();
	}
}
