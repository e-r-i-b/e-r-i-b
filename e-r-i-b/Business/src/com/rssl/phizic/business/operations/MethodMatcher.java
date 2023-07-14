package com.rssl.phizic.business.operations;

import java.lang.reflect.Method;

/**
 * Преднязначен для проверки допустимости метода в операции
 * @author Evgrafov
 * @ created 14.05.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */

public interface MethodMatcher
{
	/**
	 * @param method метод для проверки соответствия
	 * @return true = метод прошел провеку
	 */
	boolean match(Method method);
}
