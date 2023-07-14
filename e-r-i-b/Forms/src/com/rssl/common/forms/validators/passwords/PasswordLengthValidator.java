package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 06.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PasswordLengthValidator extends FieldValidatorBase
{
	private int length;

	public PasswordLengthValidator(int length)
	{
		this.length = length;
		this.setMessage("Длина пароля должна быть не менее " + length + " символов");
	}

	public static final String PASSWORD = "newPassword";

	public boolean validate(final String value) throws TemporalDocumentException
	{
		if(value.length() < length){
			return false;
		}
		return true;
	}
}
