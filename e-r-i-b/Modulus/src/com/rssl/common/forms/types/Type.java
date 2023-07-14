package com.rssl.common.forms.types;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.util.List;
import java.io.Serializable;

/**
 * Описание типа
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: khudyakov $
 * @ $Revision: 50095 $
 */
public interface Type extends Serializable
{
	/**
	 * @return Имя типа "string", "date" etc
	 */
	String getName();

	/**
	 * @return парсер по умолчаню для типа
	 */
	FieldValueParser getDefaultParser();

	/**
	 * @return валидатор по умолчанию для типа
	 */
	List<FieldValidator> getDefaultValidators();

	/**
	 * @return форматтер
	 */
	FieldValueFormatter getFormatter();
}
