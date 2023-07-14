package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Валидадор проверяет, чтобы периоды не повторялись.
 *
 * @author bogdanov
 * @ created 08.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class PayPeriodValidator extends FieldValidatorBase
{
	public PayPeriodValidator()
	{
		this("Вы неправильно указали период оплаты. Добавляемый период должен отличаться от периодов, уже добавленных на форму.");
	}

	public PayPeriodValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
			return true;

		StringTokenizer tokenizer = new StringTokenizer(value, ";");
		Set<String> payPeriodSet = new HashSet<String>();

		while (tokenizer.hasMoreTokens())
		{
			String period = tokenizer.nextToken();
			int month = Integer.parseInt(period.substring(0, period.indexOf("/")));
			if (!(1 <= month && month <= 12))
				return false;
			if (payPeriodSet.contains(period))
				return false;
			payPeriodSet.add(period);
		}

		return true;
	}
}
