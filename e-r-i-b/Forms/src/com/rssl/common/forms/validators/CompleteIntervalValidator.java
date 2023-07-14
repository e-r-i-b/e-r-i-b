package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author akrenev
 * @ created 03.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор, проверяющий целостность интервала
 */

public class CompleteIntervalValidator extends MultiFieldsValidatorBase
{
	public static final String INTERVAL_START = "start";
	public static final String INTERVAL_END = "end";
	public static final String INTERVAL_COUNT = "count";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		long count = (Long) retrieveFieldValue(INTERVAL_COUNT, values);
		int min = Integer.parseInt(getParameter(INTERVAL_START));
		int max = Integer.parseInt(getParameter(INTERVAL_END));
		//четные точки - границы интервалов, нечетные - сами интервалы.
		boolean[] crossingCheck = new boolean[2 * (max - min) + 1];
		for (int i = 0; i < count; i++)
		{
			Long startInterval = (Long) retrieveFieldValue(INTERVAL_START + i, values);
			Long endInterval = (Long) retrieveFieldValue(INTERVAL_END + i, values);
			if (startInterval != null && endInterval != null)
			{
				int start = startInterval.intValue();
				int end = endInterval.intValue();
				//если границы уже были частью интервала, то есть пересечение
				if (crossingCheck[2 * (start - min)] || crossingCheck[2 * (end - min)])
					return false;

				for (int j = 2 * start + 1; j < 2 * end; j++)
				{
					//если интервал уже задавался, то есть пересечение
					if (crossingCheck[j - min])
						return false;

					crossingCheck[j - min] = true;
				}
			}
		}
		//Все нечетные должны быть true, иначе мы не заполнили какой-то интеравал
		for (int i = 0; i < max - min;i++)
		{
			if (!crossingCheck[2 * i + 1])
				return false;
		}
		//четные могут быть любыми, false - граница, true - внутренняя точка интервала
		return true;
	}
}
