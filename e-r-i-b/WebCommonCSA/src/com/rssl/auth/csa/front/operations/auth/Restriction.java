package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.exceptions.FrontException;

/**
 * @author niculichev
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Restriction
{
	/**
	 * Проверка ограничения
	 * @return true - НЕ попадаем под ограничение
	 */
	boolean check() throws FrontException;
}
