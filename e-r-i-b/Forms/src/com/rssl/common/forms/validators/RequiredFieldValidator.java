package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: omeliyanchuk $
 * @ $Revision: 16029 $
 */

public class RequiredFieldValidator extends FieldValidatorBase
{

	/**
	 * Default ctor
	 */
	public RequiredFieldValidator()
	{
	}

	/**
	 * ctor
	 * @param message сообщение
	 */
	public RequiredFieldValidator(String message)
	{
		super();
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
    {
        return !isValueEmpty(value);
    }
}
