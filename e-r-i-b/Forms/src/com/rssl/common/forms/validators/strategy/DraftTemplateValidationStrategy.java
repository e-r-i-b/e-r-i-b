package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * Стратегия валидации полей при создании черновика шаблона
 * @author niculichev
 * @ created 08.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class DraftTemplateValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "draft-template";
	private static ValidationStrategy instance = new DraftTemplateValidationStrategy();

	private DraftTemplateValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
