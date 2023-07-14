package com.rssl.common.forms.expressions;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Krenev
 * @ created 17.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class ConstantExpression implements Expression, Serializable
{
	private Object value;

	public ConstantExpression(Object value)
	{
		this.value = value;
	}

	/**
	 * Вычислить значение выражения
	 * @param form даные формы
	 * @return значение выражения
	 */
	public Object evaluate(Map<String, Object> form)
	{
		return value;
	}
}
