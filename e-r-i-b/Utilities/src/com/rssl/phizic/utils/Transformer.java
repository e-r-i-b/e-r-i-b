package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.exceptions.SystemException;

/**
 * @author Erkin
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Преобразователь одного типа данных в другой
 * @param <Output> - тип выхода
 * @param <Input> - тип входа 
 */
public interface Transformer<Output, Input>
{
	/**
	 * Метод преобразования
	 * @param input - вход
	 * @return выход
	 */
	Output transform(Input input) throws SystemException;
}
