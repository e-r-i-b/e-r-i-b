package com.rssl.phizic.gate.exceptions;

/**
 * @author gladishev
 * @ created 11.10.2012
 * @ $Author$
 * @ $Revision$
 * Исключение таймаута на шлюзе
 */
public class GateWrapperTimeOutException extends GateTimeOutException
{
	public static final String TIMEOUT_WRAPPER_DOCUMENT_STATE_DESCRIPTION = "Получен таймаут ожидания ответа от шлюза";
	public static final String GATE_WRAPPER_TIMEOUT_DEFAULT_MESSAGE = "Операция временно недоступна. Повторите попытку позже.";
	public static final String GATE_TECHNICAL_EXCEPTION_TIMEOUT_MESSAGE = "Техническая ошибка КСШ";

	public GateWrapperTimeOutException(String message)
	{
		super(message);
	}

	public GateWrapperTimeOutException(Throwable cause)
	{
		super(cause.getMessage(), cause);
	}

	public GateWrapperTimeOutException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
