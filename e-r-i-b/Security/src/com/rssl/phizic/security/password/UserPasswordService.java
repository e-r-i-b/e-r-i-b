package com.rssl.phizic.security.password;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.RandomHelper;

/**
 * @author eMakarov
 * @ created 26.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class UserPasswordService
{
	private int length;
	private String allowedChars;

	public UserPasswordService()
	{
		PasswordValidationConfig passwordValidationConfig = ConfigFactory.getConfig(PasswordValidationConfig.class);

		length   = passwordValidationConfig.getActualPasswordLength("client");
		allowedChars = passwordValidationConfig.getPreferredAllowedCharset("client");
	}

	public int getLength()
	{
		return length;
	}

	public String getAllowedChars()
	{
		return allowedChars;
	}

	public String generate()
	{
		return RandomHelper.rand(length, allowedChars);
	}

	public String generate(PasswordStrategyValidator validator) throws SecurityException
	{
		return getRandomString(length, allowedChars, validator);
	}

	/**
	 * Получение случайной строки длиной n символов.
	 * Для составления строки используются символы из параметра chars
	 * Контроль качества осуществляется валидатором
	 * @param n длинна
	 * @param chars определяет из каких символов состоит результат
	 * @param validator валидатор стратегии
	 * @return случайная строка
	 */
	public static String getRandomString(int n, String chars, PasswordStrategyValidator validator) throws SecurityException
	{
		int tries =0;
		for (int i =10; i<10000; i++)
		{

			String randomValue = RandomHelper.rand(n, chars);
			try
			{
				if (validator.validate(randomValue))
				{
					return randomValue;
				}
			}
			catch (TemporalDocumentException e)
			{

			}
		}
		throw new SecurityException("Не удалось сгенерить случайную строку удовлетворяющую условию");
	}
}
