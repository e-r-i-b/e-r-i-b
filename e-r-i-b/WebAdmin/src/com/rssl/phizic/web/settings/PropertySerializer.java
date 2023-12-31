package com.rssl.phizic.web.settings;

/**
 * Сериализатор значений настроек
 * @author gladishev
 * @ created 07.02.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class PropertySerializer
{
	/**
	 * сериализовать значение настройки
	 * @param value - значение
	 * @return строковое представление настройки
	 */
	public abstract String serialise(Object value);
}
