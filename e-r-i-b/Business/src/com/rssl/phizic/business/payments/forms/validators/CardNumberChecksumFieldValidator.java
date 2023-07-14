package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Kidyaev
 * @ created 21.08.2006
 * @ $Author: Kidyaev $
 */
public class CardNumberChecksumFieldValidator  extends FieldValidatorBase
{
	/**
	 * Создать инстанс валидатора с заданным сообщением об ошибке
	 * @param message сообщение об ошибке
	 */
	public CardNumberChecksumFieldValidator(String message)
	{
		setMessage(message);
	}

    public CardNumberChecksumFieldValidator()
    {
        setMessage("Неверная контрольная сумма номера карты");
    }

	public boolean validate(final String value) throws TemporalDocumentException
    {
        if (isValueEmpty(value))
        {
            return true;
        }

        int length = value.length();

        if (length != 16 && length != 18 && length != 19 && length != 15)
        {
	        return true;
        }
        char checkDigit = value.charAt(length - 1);

	    char checksum = StringHelper.calculateCheckDigit(value.substring(0, length - 1));

        return checksum == checkDigit;
    }

}
