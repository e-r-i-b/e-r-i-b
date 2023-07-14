package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;

/**
 * @author Krenev
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class FieldBuilderHelper
{
	/**
	 * модифицировать начальные и вычислимые знаяния в поле заданным значением
	 * @param field поле
	 * @param value значние
	 * @return модифицированиоеописание поля
	 */

	public static Field modifyExpressions(Field field, Object value)
	{
		FieldBuilder fb = getFieldBuilder(field);
		Expression expression = new ConstantExpression(value);
		fb.setValueExpression(expression);
		fb.setInitalValueExpression(expression);
		return fb.build();
	}

	/**
	 * получить FieldBuilder, проиниализированыый атрибутами поля
	 * @param field поле.
	 * @return билдер
	 */
	public static FieldBuilder getFieldBuilder(Field field)
	{
		FieldBuilder fb = new FieldBuilder();
		fb.setName(field.getName());
		fb.setDescription(field.getDescription());
		fb.setType(field.getType().getName());
		fb.setSource(field.getSource());
		fb.setEnabledExpression(field.getEnabledExpression());
		fb.setParser(field.getParser());
		fb.setSignable(field.isSignable());
		fb.setValueExpression(field.getValueExpression());
		fb.setInitalValueExpression(field.getInitalValueExpression());
		fb.setBusinessCategory(field.getBusinessCategory());
		return fb;
	}
}
