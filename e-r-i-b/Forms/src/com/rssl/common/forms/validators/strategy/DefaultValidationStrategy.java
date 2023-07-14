package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.Field;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * Дефолтная стратегия валидации: валидируется все.
 */
public class DefaultValidationStrategy implements ValidationStrategy
{
	private static final ValidationStrategy instance = new DefaultValidationStrategy();

	/**
	 * @return инстанс стратегии.
	 */
	public static ValidationStrategy getInstance()
	{
		return instance;
	}

	public boolean accepted(Field field)
	{
		return field.getValueExpression() == null;
	}

	public boolean accepted(MultiFieldsValidator validator)
	{
		return true;
	}

	public boolean accepted(FieldValidator validator, String validatedValue)
	{
		return true;
	}
}
