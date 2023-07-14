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
	 * ƒобавить значение в кеш
	 * @param cache кеш
	 * @param key ключ, под которым будет положено добавл€емое значение
	 * @param value добавл€емое значение
	 * @param <T> кешируема€ сущноть
	 * @return добавл€емое значение
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
	 * ѕолучить кешируемую сущность из кеша, при отсутствии в кеше из соответствующего ей источника
	 * @param cache кеш
	 * @param key ключ
	 * @param action метод получени€ из источника
	 * @param <T> кешируема€ сущноть
	 * @return сущность
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
