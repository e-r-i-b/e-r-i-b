package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Gainanov
 * @ created 12.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class TaxDateFieldValidator extends FieldValidatorBase
{
	public boolean validate(String value) throws TemporalDocumentException
	{
		if(isValueEmpty(value))
		    return true;

		if(value.equals("0"))
			return true;
		DateFieldValidator dateFieldValidator = new DateFieldValidator("dd.MM.yyyy");
		return dateFieldValidator.validate(value);
	}
}
