package com.rssl.common.forms.validators;

import java.util.Map;
import java.util.Date;

/**
 * ѕровер€ет заполнение даты начала и даты окончани€.
 * ƒолжны быть заданы либо обе даты сразу, либо ни одной.
 * ≈сли обе даты заполнены, то дата начала должна быть меньше либо равна даты окончани€.
 * ¬алидатор не провер€ет наход€тс€ ли даты в будущем.
 * @author Dorzhinov
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class FilterDateValidator extends MultiFieldsValidatorBase
{
	public static final String FROM_DATE = "from";
	public static final String TO_DATE = "to";

	public boolean validate(Map values)
	{
		Date from = (Date) retrieveFieldValue(FROM_DATE, values);
		Date to = (Date) retrieveFieldValue(TO_DATE, values);

		if (from == null && to == null)
			return true;

		return validateBothDates(from, to);
	}

	protected boolean validateBothDates(Date from, Date to)
	{
		if (from == null || to == null)
		{
			setMessage("Ќеобходимо передавать даты начала и окончани€ только в паре");
			return false;
		}

		if (to.before(from))
		{
			setMessage("ƒата начала не может быть больше даты окончани€");
			return false;
		}
		return true;
	}
}

