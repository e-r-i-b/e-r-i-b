package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.generated.Status_Type;

/**
 * @author akrenev
 * @ created 22.11.13
 * @ $Author$
 * @ $Revision$
 *
 * исключение ошибки обработки запроса во врешней системе
 */

public class ExternalSystemErrorCodeException extends GateLogicException
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
	public ExternalSystemErrorCodeException(Status_Type status, Class messageType, String defaultMessage)
	{
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
