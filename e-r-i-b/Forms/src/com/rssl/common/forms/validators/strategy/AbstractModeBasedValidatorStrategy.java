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
 * Базовый класс для стратегий, основанных на значениии атрибута mode
 */
public abstract class AbstractModeBasedValidatorStrategy implements ValidationStrategy
{
	public static final String MODE_DELIMETER = "|";
	private static final String MODE_DELIMETER_REGEXP = "\\|";
	private String acceptedMode;

	protected AbstractModeBasedValidatorStrategy(String acceptedMode)
	{
		this.acceptedMode = acceptedMode;
	}

	public boolean accepted(Field field)
	{
		return field.getValueExpression() == null;
	}

	public boolean accepted(MultiFieldsValidator validator)
	{
		return accepted(validator.getMode());
	}

	public boolean accepted(FieldValidator validator, String validatedValue)
	{
		return accepted(validator.getMode());
	}

	private boolean accepted(String modes)
	{
		if (StringHelper.isEmpty(modes))
		{
			return false;
		}
		for (String mode : modes.split(MODE_DELIMETER_REGEXP))
		{
			if (mode.equals(acceptedMode))
			{
				return true;
			}
		}
		return false;
	}
}
