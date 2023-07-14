package com.rssl.common.forms.validators.passwords;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 21.02.2013
 * @ $Author$
 * @ $Revision$
 * Валидатор на количество символов подряд, расположенных в 1 горизонтальном "ряду" qwerty клавиатуры.
 * Numpad не учитывается.
 * Работаем по следующей постановке
 * Если в строке имеется подпоследовательность символов длинной не менее length, которая также содержится в сл. последовательностях(и обратных к ним)
 * 1. ё1234567890-=
 * 2. !@#$%^&*()_+
 * 3. qwertyuiop
 * 4. asdfghjkl;
 * 5. zxcvbnm,.
 * 6. йцукенгшщзхъ
 * 7. фывапролджэ
 * 8. ячсмитьбю.
 * то валидация считается непройденной. Буквы в подпоследовательностях сравниваются без учета регистра.
 */
public class QWERTYKeyboardSubsequenceLengthValidator extends FieldValidatorBase
{
	private static volatile ValidationStrategy validationStrategy;
	private static final Object LOCK = new Object();
	private static final String LENGTH = "length";
	private static final int DEFAULT_LENGTH = 3;
	private int length;

	/**
	 * Дефолтный конструктор
	 */
	public QWERTYKeyboardSubsequenceLengthValidator()
	{
		length = DEFAULT_LENGTH;
	}

	public void setParameter(String name, String value)
	{
		if (name.equals(LENGTH))
		{
			length = Integer.parseInt(value);
		}
	}

	public String getParameter(String name)
	{
		if (name.equals(LENGTH))
		{
			return String.valueOf(length);
		}
		return null;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringHelper.isEmpty(value))
		{
			return true;
		}
		return getValidationStrategy().validate(value).getFirst();
	}

	private ValidationStrategy getValidationStrategy()
	{
		if (validationStrategy != null)
		{
			return validationStrategy;
		}
		synchronized (LOCK)
		{
			if (validationStrategy != null)
			{
				return validationStrategy;
			}
			validationStrategy = createValidationStrategy();
			return validationStrategy;
		}
	}

	private ValidationStrategy createValidationStrategy()
	{
		String message = getMessage();
		
		return new ChainValidationStrategy(
				new SubsequenceLengthValidator("ё1234567890-=", length, message, true)
				, new SubsequenceLengthValidator("!@#$%^&*()_+", length, message, true)
				, new SubsequenceLengthValidator("qwertyuiop", length, message, true)
				, new SubsequenceLengthValidator("asdfghjkl;", length, message, true)
				, new SubsequenceLengthValidator("zxcvbnm,.", length, message, true)
				, new SubsequenceLengthValidator("йцукенгшщзхъ", length, message, true)
				, new SubsequenceLengthValidator("фывапролджэ", length, message, true)
				, new SubsequenceLengthValidator("ячсмитьбю.", length, message, true)
		);
	}
}
