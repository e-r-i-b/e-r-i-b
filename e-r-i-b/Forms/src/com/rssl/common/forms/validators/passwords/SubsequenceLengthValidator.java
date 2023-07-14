package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Roshka
 * @ created 28.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class SubsequenceLengthValidator extends FieldValidatorBase
{
	private static final String SEQUENCE        = "sequence";
	private static final String LENGTH          = "length";

	private static final int DEFAULT_LENGTH     = 3;
	private static final String DEFAULT_SEQUENCE = "1234567890";

	private String sequence;
	private int length;
	private final boolean ignorecase;

	/**
	 * @deprecated используем конструктор с €вными параметрами. 
	 * ƒефолтные значени€ оставлены дл€ обратной совместимости.
	 */
	@Deprecated
	public SubsequenceLengthValidator()
	{
		this(DEFAULT_SEQUENCE, DEFAULT_LENGTH, null);
	}

	public SubsequenceLengthValidator(String sequence, int length, String message)
	{
		this(sequence, length, message, false);
	}

	public SubsequenceLengthValidator(String sequence, int length, String message, boolean ignorecase)
	{
		if (sequence == null)
		{
			throw new IllegalArgumentException("ѕоследовательность не может быть null");
		}
		this.sequence = ignorecase ? sequence.toUpperCase() : sequence;
		this.length = length;
		this.ignorecase = ignorecase;
		this.setMessage(message);
	}

	public void setParameter(String name, String value)
	{
		if (name.equals(SEQUENCE))
		{
			sequence = ignorecase ? value.toUpperCase() : value;
		}
		else if (name.equals(LENGTH))
		{
			length = Integer.parseInt(value);
		}
	}

	public String getParameter(String name)
	{
		if (name.equals(SEQUENCE))
		{
			return sequence;
		}
		else if(name.equals(LENGTH))
		{
			return String.valueOf(length);
		}
		return null;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
			return true;

		String[] strings = split(ignorecase ? value.toUpperCase() : value, length);
		for (String subsequence : strings)
		{
			if( sequence.contains(subsequence) || sequence.contains(revert(subsequence)) )
				return false;
		}
		return true;
	}

	/**
	 * todo: найти нормальный способ.. ,более быстрый и надежный
	 */
	static String revert(String str)
	{
		int length = str.length();
		String result = "";

		for (int i = length - 1; i >= 0; i--)
			result += str.charAt(i);

		return result;
	}

	static String[] split(String string, int length)
	{
		if (length >= string.length())
		{
			return new String[]{string};
		}
		else
		{
			int size = string.length() - length + 1;
			String[] result = new String[size];

			for (int i = 0; i < size; i++)
				result[i] = string.substring(i, i + length);

			return result;
		}
	}
}
