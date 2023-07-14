package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author krenev
 * @ created 20.09.2010
 * @ $Author$
 * @ $Revision$
 * —тратеги€ валидации платежной формы на предмет возможности создани€ длит поручени€.
 */
public class PrepareLongOfferValidationStrategy  extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "prepare-long-offer";
	private static ValidationStrategy instance = new PrepareLongOfferValidationStrategy();

	private PrepareLongOfferValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
