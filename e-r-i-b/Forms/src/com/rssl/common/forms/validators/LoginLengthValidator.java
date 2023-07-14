package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.phizic.config.ConfigFactory;

/**
 * ¬алидатор на определение длины логина
 *
 * @author bogdanov
 * @ created 24.03.14
 * @ $Author$
 * @ $Revision$
 */

public class LoginLengthValidator extends LengthFieldValidator
{
	public boolean validate(String value) throws TemporalDocumentException
    {
        if(isValueEmpty(value))
            return true;

	    int min = ConfigFactory.getConfig(PasswordValidationConfig.class).getMinimunLoginLength();
	    if ((minlength != null && maxlength != null) && (minlength.intValue() > min || min > maxlength.intValue()))
		    throw new TemporalDocumentException("Ќе правильно установлено значение настройки минимальной длины логина");

        if(value.length() < min)
            return false;

        if (maxlength != null && value.length() > maxlength.intValue())
            return false;

        return true;
    }

	@Override
	public String getMessage()
	{
		return String.format(super.getMessage(), ConfigFactory.getConfig(PasswordValidationConfig.class).getMinimunLoginLength());
	}
}
