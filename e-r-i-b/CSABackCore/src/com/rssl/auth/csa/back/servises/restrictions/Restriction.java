package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Restriction<T>
{
	/**
	 * Проверить ограничение на что-либо
	 * @param object объек для проверки
	 * @throws RestrictionException в случае нарушения ограничения.
	 */
	void check(T object) throws Exception;
}
