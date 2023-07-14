package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.List;

/**
 * @author Erkin
 * @ created 14.10.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * Преобразователь списка одного типа в другой
 * @param <Output> - тип элемента выходного списка
 * @param <Input> - тип элемента входного списка
 */
public interface ListTransformer<Output, Input>
{
	/**
	 * Метод преобразования
	 * @param input - вход
	 * @return выход
	 */
	List<Output> transform(List<Input> input) throws SystemException;
}
