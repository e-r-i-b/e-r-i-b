package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.types.SubType;

/**
 * Стратегия
 *
 * @author khudyakov
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ByTemplateValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "by-template";

	private static final ValidationStrategy instance = new ByTemplateValidationStrategy();

	private ByTemplateValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}

	public boolean accepted(Field field)
	{
		if (SubType.STATIC == field.getSubType())
		{
			return true;
		}

		return super.accepted(field);
	}
}
