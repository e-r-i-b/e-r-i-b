package com.rssl.common.forms.validators;

/**
 * @author Gulov
 * @ created 11.06.2010
 * @ $Authors$
 * @ $Revision$
 */
public class MultiLineTextValidator extends FieldValidatorBase
{
	private static final String SYMBOLS = "символов";
	private int count = 0;

	public MultiLineTextValidator(String messagePart, int count)
	{
		super();
		this.count = count;
		setMessage(messagePart.trim() + " " + Integer.toString(this.count) + " " + SYMBOLS);
	}

	public boolean validate(String value)
	{
		if (isValueEmpty(value))
			return true;

		String temp = value.replace("\r\n", " ");

		if (temp.length() <= count)
			return true;

		return false;
	}
}
