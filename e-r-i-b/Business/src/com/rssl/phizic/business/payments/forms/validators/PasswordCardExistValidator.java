package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.auth.passwordcards.PasswordCardService;
import com.rssl.phizic.auth.passwordcards.PasswordCard;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 23.11.2006 Time: 8:57:50 To change this template use
 * File | Settings | File Templates.
 */
public class PasswordCardExistValidator extends FieldValidatorBase
{
	private static final PasswordCardService cardService = new PasswordCardService();

	public PasswordCardExistValidator()
	{
		this("Карты с таким номером не существует");
	}

    public PasswordCardExistValidator(String message)
    {
        setMessage(message);
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
	    try
	    {
		  PasswordCard card = cardService.findByNumber(value);

		  return (card != null);
	    }
	    catch( SecurityDbException ex)
	    {
		    return false;
	    }
    }
}
