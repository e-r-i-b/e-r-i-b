package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.PropertyConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author vagin
 * @ created 09.09.14
 * @ $Author$
 * @ $Revision$
 * Валидатор проверки соответствия введенного значения процента с настройками в админке.
 */
public class MoneyBoxPercentValidator extends FieldValidatorBase
{
	private static final String MESSAGE_PATTERN = "Процент должен быть не меньше %s и не больше %s. Укажите другое значение";
	private static final long MAX_PERCENT = 100L;
	private static final long MIN_PERCENT = 1L;
	private ThreadLocal<String> message = new ThreadLocal<String>();

	public boolean validate(String percent) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(percent))
		{
			throw new TemporalDocumentException("Не указан процет перевода, обязательный для данного вида копилки.");
		}

		long percentValue = Long.valueOf(percent);

		PropertyConfig config = ConfigFactory.getConfig(PropertyConfig.class);
		String minPercent = config.getProperty("com.rssl.iccs.moneybox.percent.min");
		String maxPercent = config.getProperty("com.rssl.iccs.moneybox.percent.max");

		long minPercentLong = StringHelper.isEmpty(minPercent) ? MIN_PERCENT : Long.valueOf(minPercent);
		minPercentLong = minPercentLong < MIN_PERCENT ? MIN_PERCENT : minPercentLong;

		long maxPercentLong = StringHelper.isEmpty(maxPercent) ? MAX_PERCENT : Long.valueOf(maxPercent);
		maxPercentLong = maxPercentLong > MAX_PERCENT ? MAX_PERCENT : maxPercentLong;

		message.set(String.format(MESSAGE_PATTERN, minPercentLong, maxPercentLong));

		return minPercentLong <= percentValue && percentValue <= maxPercentLong;
	}

	public String getMessage()
	{
		return message.get();
	}
}
