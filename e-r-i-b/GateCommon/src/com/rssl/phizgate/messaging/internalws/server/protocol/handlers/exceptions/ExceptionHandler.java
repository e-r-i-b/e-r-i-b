package com.rssl.phizgate.messaging.internalws.server.protocol.handlers.exceptions;

import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseBuilder;
import com.rssl.phizgate.messaging.internalws.server.protocol.ResponseInfo;

/**
 * Дефолтный обработчик исключений, возникших при обработке запросов
 * @author gladishev
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionHandler
{
	private int errorCode;
	private String errorDescription;

	/**
	 * ctor
	 * @param errorCode - Код ошибки, отправляемое в ответе на запрос
	 * @param errorDescription - описание ошибки, отправляемое в ответе
	 */
	public ExceptionHandler(int errorCode, String errorDescription)
	{
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

	/**
	 * Обработка исключения
	 * @param responceType - тип ответа
	 * @param e - исключение
	 * @return информация об ответе
	 */
	public ResponseInfo handle(String responceType, Exception e) throws Exception
	{
		return new ResponseBuilder(responceType, errorCode, errorDescription + ":" + e).end();
	}
}
