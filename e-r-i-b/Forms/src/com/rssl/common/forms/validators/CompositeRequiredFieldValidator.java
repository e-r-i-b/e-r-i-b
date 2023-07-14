package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Валидатор, проверяющий что хотя бы одно из переданных в параметрах полей полей не пустое.
 * @author niculichev
 * @ created 18.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class CompositeRequiredFieldValidator extends MultiFieldsValidatorBase
{
	public CompositeRequiredFieldValidator()
	{
	}

	public CompositeRequiredFieldValidator(String message)
	{
		super();
		setMessage(message);
	}


	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getParametersNames();
		for (String name : names)
		{
			Object value = values.get(getParameter(name));
			// если значение не null и не пустая строка
			if (value != null && !((value instanceof String) && (StringHelper.isEmpty((String) value))))
				return true;
		}

		return false;
	}
}
