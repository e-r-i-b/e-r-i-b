package com.rssl.phizic.security.password;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Calendar;

/**
 * @author Kidyaev
 * Генератор пароля, использующий SHA1PRNG алгоритм генерации случайных чисел
 * @ created 22.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class SecurePasswordValueGenerator implements PasswordValueGenerator
{
    private SecureRandom generator;

	public SecurePasswordValueGenerator ()
	{
		try
		{
			init("SHA1PRNG");
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchProviderException e)
		{
			throw new RuntimeException(e);
		}
	}

	public SecurePasswordValueGenerator (String algorithm) throws NoSuchProviderException, NoSuchAlgorithmException
	{
		init(algorithm);
	}

	private void init(String algorithm) throws NoSuchAlgorithmException, NoSuchProviderException {
		generator = SecureRandom.getInstance(algorithm);
		generator.setSeed( Calendar.getInstance().getTimeInMillis() );
	}

	public char [] newPassword(int length, char [] allowedChars)
	{
		char [] password = new char [length];

		for (int i = 0; i < length; i++)
		{
			password[i] = allowedChars[ generator.nextInt(allowedChars.length) ];
		}

		return password;
	}
}
