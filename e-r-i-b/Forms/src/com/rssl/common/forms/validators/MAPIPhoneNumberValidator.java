package com.rssl.common.forms.validators;

/**
 * @author tisov
 * @ created 18.12.14
 * @ $Author$
 * @ $Revision$
 * ¬алидаци€ номера телефона по формату, используемому в сообщени€х с мјѕ»
 */
public class MAPIPhoneNumberValidator extends RegexpFieldValidator
{
	private static final String PATTERN = "(7\\d{10})";

	public MAPIPhoneNumberValidator()
	{
		super(PATTERN);
	}

}
