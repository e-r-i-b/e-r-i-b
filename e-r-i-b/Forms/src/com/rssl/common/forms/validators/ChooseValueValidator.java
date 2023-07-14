package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Krenev
 * @ created 11.01.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕровер€ет совпадение зна€ни€ пол€ одному из параметров
 * (использовать дл€ фиксрованных списков).
 */
public class ChooseValueValidator extends FieldValidatorBase
{
	private Collection<String> values;

	public ChooseValueValidator()
	{
		values = new ArrayList<String>();
	}

	public ChooseValueValidator(Collection<String> values)
	{
		this.values = values;
	}

	public ChooseValueValidator(String... values)
	{
		this.values = Arrays.asList(values);
	}

	public ChooseValueValidator(Collection<String> values, String msg)
	{
		this.values = values;
		this.setMessage(msg);
	}

	public void setParameter(String name, String value)
	{
		values.add(value);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
			return true;
		
		for (String expectedValue : values)
		{
			//¬ формпроцессоре перед валидацией пол€ трим€тс€, поэтому, если значение было с пробелами в конце
			//или начале, данна€ валидаци€ не пройдет. “римим значени€ перед сравнением с пользовательским.
			if (expectedValue.trim().equals(value))
			{
				return true;
			}
		}
		return false;
	}
}
