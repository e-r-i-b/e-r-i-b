package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 22.11.2006 Time: 18:16:50 To change this template use
 * File | Settings | File Templates.
 */
public class PasswordCardNumberValidator extends FieldValidatorBase
{
    public PasswordCardNumberValidator()
    {
        this("Карта ключей с таким номером не существует");
    }

    public PasswordCardNumberValidator(String message)
    {
        setMessage(message);
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
	  //см. CHG001169
      return true;
    }
}
