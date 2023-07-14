package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.common.forms.Field;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * Стратегия валидации черновика: валидируются только заполенные значения.
 * MultiFieldsValidator'ы доступны все
 */
public class DraftValidationStrategy implements ValidationStrategy
{
	private static ValidationStrategy instance = new DraftValidationStrategy();

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
		return !StringHelper.isEmpty(validatedValue);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
