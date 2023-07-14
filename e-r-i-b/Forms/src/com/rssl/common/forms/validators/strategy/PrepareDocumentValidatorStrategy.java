package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author krenev
 * @ created 05.04.2011
 * @ $Author$
 * @ $Revision$
 * Стратегия валидации подготовки платежа.
 */
public class PrepareDocumentValidatorStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "prepare";

	private PrepareDocumentValidatorStrategy()
	{
		super(MODE);
	}

	private static final ValidationStrategy instance = new PrepareDocumentValidatorStrategy();

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
