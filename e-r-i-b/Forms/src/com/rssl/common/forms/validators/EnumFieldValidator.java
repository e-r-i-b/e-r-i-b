package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Erkin
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ��������� �������� �� ��������� � enum
 * @param <T>
 */
public class EnumFieldValidator<T extends Enum<T>> extends FieldValidatorBase
{
	private final Class<T> enumClass;

	/**
	 * ctor
	 * @param enumClass - ����� enum-�
	 */
	public EnumFieldValidator(Class<T> enumClass)
	{
		this.enumClass = enumClass;
	}

	/**
	 * ctor
	 * @param enumClass - ����� enum-�
	 * @param message
	 */
	public EnumFieldValidator(Class<T> enumClass, String message)
	{
		this(enumClass);
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (isValueEmpty(value))
			return true;

		try
		{
			Enum.valueOf(enumClass, value);
			return true;
		}
		catch (IllegalArgumentException ignored)
		{
			// ���� �������� �� �������� � enum, ���� enumClass - �� enum
			return false;
		}
	}
}
