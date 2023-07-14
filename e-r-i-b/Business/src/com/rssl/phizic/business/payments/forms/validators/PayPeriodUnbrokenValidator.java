package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.StringTokenizer;

/**
 * Валидатор проверяет, чтобы периоды следовали друг за другом как требует мод "UNBROKEN"
 * @author Gainanov
 * @ created 09.03.2010
 * @ $Author$
 * @ $Revision$
 */
public class PayPeriodUnbrokenValidator extends FieldValidatorBase
{
	public PayPeriodUnbrokenValidator()
	{
		this("Вы неправильно указали период оплаты. Добавляемый период должен предшествовать или следовать за тем периодом, который уже добавлен на форму.");
	}

	public PayPeriodUnbrokenValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (StringHelper.isEmpty(value))
				return true;
		
			StringTokenizer tokenizer = new StringTokenizer(value, ";");
			int count = tokenizer.countTokens();
			int[] years = new int[count];
			int[] months = new int[count];
			int i = 0;
			while (tokenizer.hasMoreTokens())
			{
				String period = tokenizer.nextToken();
				int month = Integer.parseInt(period.substring(0, period.indexOf("/")));
				if (month > 12 || month < 1)
					return false;
				int year = Integer.parseInt(period.substring(period.indexOf("/") + 1, period.length()));
				years[i] = year;
				months[i++] = month;
			}
			int minYear = years[0];
			int minYearIndex = 0;
			for (i = 1; i < count; i++)
			{
				if (years[i] < minYear)
				{
					minYear = years[i];
					minYearIndex = i;
				}
			}
			int minMonth = months[minYearIndex];
			int minMonthIndex = minYearIndex;
			for (i = 0; i < count; i++)
			{
				if (months[i] < minMonth && years[i] == minYear)
				{
					minMonth = months[i];
					minMonthIndex = i;
				}
			}

			for (i = 1; i < count; i++)
			{
				int checkMonth = (minMonth + i > 12) ? 12 - minMonth : minMonth + i;
				int checkYear = (minMonth + i > 12) ? minYear + ((minMonth + i) / 12) : minYear;
				boolean checked = false;
				for (int j = 0; j < count; j++)
				{
					if (months[j] == checkMonth && years[j] == checkYear)
						checked = true;
				}
				if (!checked)
					return false;
			}

			return true;
		}catch(NumberFormatException e)
		{
			return false;
		}
	}
}
