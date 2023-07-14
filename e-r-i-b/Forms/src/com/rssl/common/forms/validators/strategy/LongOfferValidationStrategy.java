package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * Стратегия валидации платежной формы длит поручения.
 *
 * @author hudyakov
 * @ created 01.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "long-offer";
	private static ValidationStrategy instance = new LongOfferValidationStrategy();

	private LongOfferValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
