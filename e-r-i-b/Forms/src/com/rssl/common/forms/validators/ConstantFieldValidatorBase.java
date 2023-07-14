package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

/**
 * @author Erkin
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ��������� �������� �� ��������������� ���������
 */
public abstract class ConstantFieldValidatorBase<T> extends FieldValidatorBase
{
	private final T constant;

	protected ConstantFieldValidatorBase(T constant)
	{
		this.constant = constant;
	}

	protected ConstantFieldValidatorBase(T constant, String message)
	{
		this(constant);
		setMessage(message);
	}

	public boolean validate(String string) throws TemporalDocumentException
	{
		if (isValueEmpty(string))
			return true;

		try
		{
			T value = valueOf(string);
			return constant.equals(value);
		}
		catch (IllegalArgumentException ignored)
		{
			// valueOf() �� ���� ���������� string
			return false;
		}
	}

	protected abstract T valueOf(String string) throws IllegalArgumentException;
}
