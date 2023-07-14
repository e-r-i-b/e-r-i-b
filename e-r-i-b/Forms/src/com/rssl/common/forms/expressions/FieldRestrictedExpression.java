package com.rssl.common.forms.expressions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 30.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Выражение проверяющее поле на допустимые значения
 */

public class FieldRestrictedExpression implements Expression
{
	private String fieldName;
	private List enabledTypes;

	/**
	 * конструктор
	 * @param fieldName имя поля
	 * @param values допустимые значения
	 */
	public FieldRestrictedExpression(String fieldName, Object... values)
	{
		this.fieldName = fieldName;
		this.enabledTypes = Arrays.asList(values);
	}

	public Object evaluate(Map<String, Object> form)
	{
		return enabledTypes.contains(form.get(fieldName));
	}
}