package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * @author akrenev
 * @ created 26.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * исключение ошибки обработки запроса во врешней системе (таймаут)
 */

public class ExternalSystemTimeoutErrorCodeException extends GateTimeOutException
{
	private final Status_Type status;
	private final Class messageType;
	@SuppressWarnings("NonFinalFieldOfException")
	private String errorMessage;

	/**
	 * конструктор
	 * @param status статус ответа
	 * @param messageType тип ответа
	 * @param defaultMessage сообщение
	 */
	public ExternalSystemTimeoutErrorCodeException(Status_Type status, Class messageType, String defaultMessage)
	{
		super(defaultMessage);
		this.status = status;
		this.messageType = messageType;
		errorMessage = defaultMessage;
	}

	/**
	 * задать новое сообщение
	 * @param errorMessage новое сообщение
	 */
	public void setErrorMessage(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}

	/**
	 * @return статус ответа
	 */
	public Status_Type getStatus()
	{
		return status;
	}

	/**
	 * @return тип ответа
	 */
	public Class getMessageType()
	{
		return messageType;
	}

	@Override
	public String getMessage()
	{
		return errorMessage;
	}
}
