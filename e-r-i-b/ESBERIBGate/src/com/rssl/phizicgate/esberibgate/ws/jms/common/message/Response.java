package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 13.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ответ шины через jms
 */

public class Response<T>
{
	private final String messageId;
	private final String responseBody;
	private final String errorCode;
	private final String errorMessage;
	private final T response;

	/**
	 * конструктор
	 * @param messageId идентификатор сообщения
	 * @param responseBody тело ответа
	 * @param response объект ответа
	 * @param errorCode код ошибки
	 * @param errorMessage текст ошибки
	 */
	public Response(String messageId, String responseBody, T response, String errorCode, String errorMessage)
	{
		this.messageId = messageId;
		this.responseBody = responseBody;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.response = response;
	}

	/**
	 * @return идентификатор сообщения
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * @return тело ответа
	 */
	public String getResponseBody()
	{
		return responseBody;
	}

	/**
	 * @return код ошибки
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @return текст ошибки
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @return объектное представление ответа
	 */
	public T getResponse()
	{
		return response;
	}
}
