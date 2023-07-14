package com.rssl.phizic.gate.statistics.exception;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Интерфейс сбора статистики по исключениям
 */

public interface ExceptionStatisticService extends Service
{
	/**
	 * добавить информацию об исключении
	 * @param exceptionInfo информация об ошибке
	 * @return сообщение клиенту
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public String addException(ExternalExceptionInfo exceptionInfo) throws GateLogicException, GateException;
}
