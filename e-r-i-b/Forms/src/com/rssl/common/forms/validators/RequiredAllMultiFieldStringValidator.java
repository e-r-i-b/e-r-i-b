package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author vagin
 * @ created 25.02.15
 * @ $Author$
 * @ $Revision$
 * Определяет, все ли поля имеют непустое (!= null && !='') значение.
 */
public class RequiredAllMultiFieldStringValidator extends MultiFieldsValidatorBase
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getBindingsNames();
		for (String name : names)
		{
			Object value = retrieveFieldValue(name, values);
			if (value == null || (value instanceof String && StringHelper.isEmpty((String) value)))
				return false;
		}
		return true;
	}
}
