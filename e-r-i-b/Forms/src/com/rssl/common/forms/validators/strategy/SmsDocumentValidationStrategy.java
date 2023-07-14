package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author Erkin
 * @ created 19.03.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Стратегия валидации для СМС-канала ЕРМБ и МБК
 */
public class SmsDocumentValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "sms";

	private static final ValidationStrategy instance = new SmsDocumentValidationStrategy();

	private SmsDocumentValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
