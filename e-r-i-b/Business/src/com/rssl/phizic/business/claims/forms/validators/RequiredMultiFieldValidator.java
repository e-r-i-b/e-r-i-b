package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Определяет имеет ли хотя бы одно поле непустое значение
 * @author eMakarov
 * @ created 23.11.2007
 * @ $Author$
 * @ $Revision$
 */
public class RequiredMultiFieldValidator extends MultiFieldsValidatorBase
{
	private boolean isValueEmpty(Object value)
	{
	    if (value == null || value.toString().length()==0)
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
