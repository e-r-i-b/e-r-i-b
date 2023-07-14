package com.rssl.common.forms.expressions;


import java.util.Map;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 05.09.2007
 * @ $Author$
 * @ $Revision$
 */

public interface Expression
{
	/**
	 * Вычислить значение выражения
	 * @param form даные формы
	 * @return значение выражения
	 */
	Object evaluate(Map<String, Object> form);
}
