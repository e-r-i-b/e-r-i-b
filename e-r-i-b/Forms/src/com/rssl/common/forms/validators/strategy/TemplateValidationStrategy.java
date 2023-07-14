package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * Стратегия валидации шаблонов:валидатор используется, если в атрибуте mоde есть значение template
 */
public class TemplateValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "template";
	private static ValidationStrategy instance = new TemplateValidationStrategy();

	private TemplateValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
