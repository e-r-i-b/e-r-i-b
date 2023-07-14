package com.rssl.common.forms.validators;

import java.util.Date;
import java.util.Map;

/**
 * Проверяет заполнение даты начала, даты окончания и кол-ва записей.
 * Должны быть заданы либо обе даты сразу, либо кол-во записей.
 * Если обе даты заполнены, то дата начала должна быть меньше либо равна даты окончания.
 * Валидатор не проверяет находятся ли даты в будущем.
 * @author Rydvanskiy
 * @ created 02.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class FilterDateCountValidator extends FilterDateValidator
{
	public static final String COUNT = "count";

	public boolean validate(Map values)
	{
		Long count = (Long) retrieveFieldValue(COUNT, values);
		Date from = (Date) retrieveFieldValue(FROM_DATE, values);
		Date to = (Date) retrieveFieldValue(TO_DATE, values);

		if (count == null)
		{
			if (from == null && to == null)
			{
				setMessage("Необходимо передавать либо даты начала и окончания, либо количество последних записей");
				return false;
			}

			return validateBothDates(from, to);
		}
		if (count == 0)
		{
			setMessage("Невозможно вернуть указанное количество операций");
			return false;
		}
		return true;
	}
}
