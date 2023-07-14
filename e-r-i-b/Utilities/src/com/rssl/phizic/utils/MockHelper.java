package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.MockObject;

/**
 * @author Erkin
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Хелпер для работы с заглушками
 */
public class MockHelper
{
	/**
	 * Отвечает на вопрос: "Является ли указанный объект заглушкой?"
	 * @param object - объект с подозрением на заглушку
	 * @return true, если указали null либо объект с интерфейсом MockObject
	 */
	public static boolean isMockObject(Object object)
	{
		if (object == null)
			return true;
		
		return (object instanceof MockObject);
	}
}
