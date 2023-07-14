package com.rssl.common.forms.types;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 04.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 50095 $
 */

public interface FieldValueFormatter extends Serializable
{
	/**
	 * ѕреобразует значение пол€ в формат дл€ подписи
	 * @param value значение пол€
	 * @return строковое представление пол€ в формате дл€ подписи
	 */
	String toSignableForm(String value);
}