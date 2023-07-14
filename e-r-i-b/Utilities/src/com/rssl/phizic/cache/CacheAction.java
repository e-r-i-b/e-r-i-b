package com.rssl.phizic.cache;

/**
 * Получить кешируюемю сущность из соответствующего ей источника
 *
 * @author khudyakov
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface CacheAction<T>
{
	/**
	 * Получить кешируюемю сущность из соответствующего ей источника
	 * @return кешируемая сущность
	 * @throws Exception
	 */
	T getEntity() throws Exception;
}
