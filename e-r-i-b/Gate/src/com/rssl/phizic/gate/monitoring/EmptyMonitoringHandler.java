package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author mihaylov
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Пустой хендлер мониторинга для систем в которых он(мониторинг) ещё не реализован
 */
public class EmptyMonitoringHandler extends MonitoringHandler
{
	public void processRequest(String requestTag, long time, String errorCode)
	{
		//ничего не делаем
	}

	public void checkRequest(String requestTag) throws GateLogicException
	{
		//ничего не делаем
	}

	public void processError(String requestTag)
	{
		//ничего не делаем
	}

	public void processResponce(String responceTag, String errorCode)
	{
		//ничего не делаем
	}

	public void processOk(String responceTag)
	{
		//ничего не делаем
	}
}
