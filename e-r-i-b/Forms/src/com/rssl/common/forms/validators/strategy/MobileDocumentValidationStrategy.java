package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author Erkin
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Стратегия валидации платежей в мобильных приложениях
 */
public class MobileDocumentValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "mobile";

	private static final ValidationStrategy instance = new MobileDocumentValidationStrategy();

	private MobileDocumentValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
