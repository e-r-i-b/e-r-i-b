package com.rssl.phizic.gate.monitoring.fraud;

import com.rssl.phizic.gate.monitoring.fraud.ws.generated.Response_Type;
import com.rssl.phizic.gate.monitoring.fraud.ws.generated.StatusHeader_Type;

/**
 * Хелпер
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ResponseHelper
{
	/**
	 * Создать запрос
	 *
	 * @param message сообщение
	 * @param code код
	 * @return сообщение об ошибка
	 */
	public static Response_Type buildResponse(String message, int code, int reasonCode)
	{
		return new Response_Type(new StatusHeader_Type(message, code, reasonCode));
	}

	/**
	 * Создать запрос
	 *
	 * @param message сообщение
	 * @return сообщение об ошибка
	 */
	public static Response_Type buildErrorResponse(String message)
	{
		return new Response_Type(new StatusHeader_Type(message, Constants.PROCESSING_REQUEST_ERROR_CODE, Constants.DEFAULT_REASON_CODE));
	}
	/**
	 * Создать запрос
	 * @return сообщение об ошибка
	 */
	public static Response_Type buildSuccessResponse()
	{
		return new Response_Type(new StatusHeader_Type(Constants.PROCESSING_REQUEST_SUCCESS_CODE_DESCRIPTION, Constants.PROCESSING_REQUEST_SUCCESS_CODE, Constants.DEFAULT_REASON_CODE));
	}
}
