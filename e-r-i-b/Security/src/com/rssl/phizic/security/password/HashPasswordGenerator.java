package com.rssl.phizic.security.password;

/**
 * @author mihaylov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * "Пустой" генератор пароля. Преобразует входную строку к массиву символов.
 */
public class HashPasswordGenerator implements PasswordValueGenerator
{
	private String password;

	/**
	 * Конструктор
	 * @param password - пароль для преобразования к массиву
	 */
	public HashPasswordGenerator(String password)
	{
		this.password = password;
	}

	public char[] newPassword(int length, char[] allowedChars)
	{
		return password.toCharArray();
	}
}
