package com.rssl.phizicgate.mobilebank;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mobilebank.ImsiCheckResult;

/**
 * Утилитный класс для работы с результатом проверки IMSI
 * @author Jatsky
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */

public class ImsiUtils
{
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
			case 1305200001:
			case 1305200002:
			case 1305200003:
			case 1305200004:
			case 1306060001:
			case 1306060002:
			case 1306060003:
			case 1306060004:
			case 1306060005:
			case 1306060006:
				return ImsiCheckResult.imsi_check_ok;
			case 1305200005:
			case 1306060083:
				return ImsiCheckResult.quarantine;
			case 1305200006:
			case 1306060082:
				return ImsiCheckResult.blocked;
			case 1305209999:
			case 1306068888:
				return ImsiCheckResult.error;
			case 1306060000:
				return ImsiCheckResult.imsi_ok;
			case 1306060081:
				return ImsiCheckResult.client_error;
			case 1306060084:
				return ImsiCheckResult.imsi_fail;
			case 1306068881:
				return ImsiCheckResult.msg_not_found;
			case 1306068882:
				return ImsiCheckResult.yet_not_check;
			default:
				return ImsiCheckResult.UNKNOWN;
		}
	}

	/**
	 * Проверка кода возврата ХП
	 * @param rc код возврата
	 * @throws java.sql.SQLException
	 */
	static void testReturnCode(int rc) throws GateException
	{
		StandInUtils.checkStandInAndThrow(rc);
		ImsiCheckResult imsiResult = getImsiResult(rc);
		if (rc != 0 && imsiResult != ImsiCheckResult.imsi_check_ok)
			throw new GateException(imsiResult.getDescription());
	}
}

