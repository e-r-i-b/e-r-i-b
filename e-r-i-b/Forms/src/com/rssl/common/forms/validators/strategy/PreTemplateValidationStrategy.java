package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.processing.ValidationStrategy;
import com.rssl.common.forms.types.SubType;

/**
 * @author osminin
 * @ created 01.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Стратегия валидации шаблонов для mode "pre-template"
 */
public class PreTemplateValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "pre-template";
	
	private static ValidationStrategy instance = new PreTemplateValidationStrategy();

	private PreTemplateValidationStrategy()
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
