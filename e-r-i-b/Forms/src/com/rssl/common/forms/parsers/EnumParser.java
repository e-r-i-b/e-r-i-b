package com.rssl.common.forms.parsers;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Парсер енумов
 */

public class EnumParser<E extends Enum<E>> implements FieldValueParser<E>
{
	private final Class<E> enumClass;

	/**
	 * конструктор
	 * @param enumClass класс енума, который хотим получить после парсинга
	 */
	public EnumParser(Class<E> enumClass)
	{
		this.enumClass = enumClass;
	}

	public E parse(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		return Enum.valueOf(enumClass, value);
	}
}
