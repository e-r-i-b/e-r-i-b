package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;

import java.sql.SQLException;

/**
 * Утилитный класс для работы с отправкой смс через МБК
 * @author Puzikov
 * @ created 23.09.14
 * @ $Author$
 * @ $Revision$
 */

class SendSmsUtils
{
	private static final int SMS_TEXT_MAX_LENGTH = 500;
	private static final String SMS_LENGTH_ERROR = "Ошибка! Превышена длина смс сообщения [ %s ]";

	/**
	 * Проверить максимальную длину смс и выбросить исключение
	 * @param text текст смс
	 * @param textToLog маскированный текст смс
	 * @throws GateException
	 */
	static void smsLengthCheckAndThrow(String text, String textToLog) throws GateException
	{
		if (text.length() > SMS_TEXT_MAX_LENGTH)
			throw new GateException(String.format(SMS_LENGTH_ERROR, textToLog));
	}

	/**
	 * Проверка кода возврата ХП
 	 * @param rc код возврата
	 * @throws SQLException
	 */
	static void testReturnCode(int rc) throws SQLException
	{
		StandInUtils.checkStandInAndThrow(rc);
		if (rc != 0)
			throw new SQLException("неизвестный код возврата (" + rc + ")");
	}

	/**
	 * Обработка кода проверки IMSI
	 * @param code код
	 * @return результат проверки IMSI
	 */
	static ImsiCheckResult getImsiResult(Integer code)
	{
		if (code == null)
			return ImsiCheckResult.UNKNOWN;

		switch (code)
		{
			case 1202082000:
			case 1202082001:
			case 1202082002:
			case 1202082003:
			case 1202082004:
				return ImsiCheckResult.send;
			case 1202084009:
				return ImsiCheckResult.yet_not_send;
			case 1202083044:
				return ImsiCheckResult.client_error;
			case 1202083055:
				return ImsiCheckResult.imsi_error;
			default:
				return ImsiCheckResult.UNKNOWN;
		}
	}
}
