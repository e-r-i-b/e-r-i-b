package com.rssl.common.forms.processing;

import java.io.Serializable;
import java.util.Map;

/**
 * Источник значеий полей для работы с формами
 * @author Evgrafov
 * @ created 15.12.2005
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */
public interface FieldValuesSource extends Serializable
{
	/**
	 * Возвращает значение поля по его имени
	 * @param name имя поля
	 * @return значение поля
	 */
	String getValue(String name);

	/**
	 * @return все значения в виде пары (ключ-значение)
	 */
	Map<String, String> getAllValues();

	/**
	 *
	 * @param name имя поля
	 * @return измененное поле или нет
	 */
	boolean isChanged(String name);

	/**
	 * @return пуст ли источник.
	 */
	boolean isEmpty();

	/**
	 * Накладывается ли маска в данном источнике на значение поля с именем name
	 * @param name имя поля
	 * @return true - накладывается
	 */
	boolean isMasked(String name);
}
