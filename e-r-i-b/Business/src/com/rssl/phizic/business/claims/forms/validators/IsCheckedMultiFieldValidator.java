package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Определяет имеет ли хотя бы одно поле значение  true
 * @author lukina
 * @ created 04.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class IsCheckedMultiFieldValidator  extends RequiredMultiFieldValidator
{
	private boolean isValueEmpty(Object value)
	{
	    if (!(Boolean)value)
	        return true;
	    return false;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getBindingsNames();
		for (String name : names)
		{
			if (!isValueEmpty(retrieveFieldValue(name, values)))
				return true;
		}
		return false;
	}
}
