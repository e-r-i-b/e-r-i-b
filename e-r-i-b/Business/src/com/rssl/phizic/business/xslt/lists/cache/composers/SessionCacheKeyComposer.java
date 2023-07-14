package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;

import java.util.List;

/**
 * Композер для получения списка сессионных ключей по объекту.
 *
 * Например карточка может быть привязана к двум пользователям
 * таким образом метод getSessionKeys должен вычислить пользователей
 * к которым она привязана и по их идентификаторам сформировать ключи
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SessionCacheKeyComposer extends CacheKeyComposer
{
	/**
	 * @param object объект по которому будем собирать ключи
	 * @return список сессионных ключей
	 */
	List<String> getSessionKeys(Object object) throws BusinessException;
}
