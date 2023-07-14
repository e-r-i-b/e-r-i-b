package com.rssl.phizic.security;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.RandomHelper;

/**
 * Генерация одноразовых паролей для смс-канала
 * @author Puzikov
 * @ created 08.09.14
 * @ $Author$
 * @ $Revision$
 */

class OneTimePasswordGeneratorSmsChannel
{
	private static final int MAX_TRIES = 10000;

	private final int length = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();

	/**
	 * сгенерировать код подтверждения по правилам смс-канала ЕРМБ:
	 * случайная последовательность цифр с ограничениями
	 * @return строка кода подтверждения
	 */
	String generate()
	{
		for (int i = 0; i < MAX_TRIES; i++)
		{
			String password = RandomHelper.rand(length, RandomHelper.DIGITS);
			if (check(password))
				return password;
		}
		throw new InternalErrorException("Не удалось сгенерить пароль");
	}

	//1.	Исключить возможность генерации значения кода равного 10000;
	//2.	Исключить возможность генерации значений кода с первым символом «0».
	private boolean check(String password)
	{
		if (password.startsWith("0"))
			return false;

		if ("10000".equals(password))
			return false;

		return true;
	}
}
