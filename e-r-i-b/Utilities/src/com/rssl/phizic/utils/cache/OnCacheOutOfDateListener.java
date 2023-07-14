package com.rssl.phizic.utils.cache;

/**
 * Объект, выдающий данные, которые необходимо закешировать.
 *
 * @author bogdanov
 * @ created 18.09.14
 * @ $Author$
 * @ $Revision$
 */

public interface OnCacheOutOfDateListener<KeyType, ReturnType>
{
	/**
	 * @return значение для кеширования.
	 */
	ReturnType onRefresh(KeyType key);
}
