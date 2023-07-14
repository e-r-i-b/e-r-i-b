package com.rssl.phizic.business.xslt.lists.cache.composers;

/**
 * композер для формирования ключа в колбаккеше для объекта
 * @author gladishev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

public interface CacheKeyComposer
{
	/**
	 * @param object - объект по которому создается ключ
	 * @return - ключ
	 */
	String getKey(Object object);
}
