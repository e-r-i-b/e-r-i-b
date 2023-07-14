package com.rssl.phizicgate.mdm.integration.mdm.message;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ответ шины через jms
 */

public class Response<T>
{
	private final String messageId;
	private final String responseBody;
	private final T response;

	/**
	 * конструктор
	 * @param messageId идентификатор сообщения
	 * @param responseBody тело ответа
	 * @param response объект ответа
	 */
	public Response(String messageId, String responseBody, T response)
	{
		this.messageId = messageId;
		this.responseBody = responseBody;
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
	 * @return объектное представление ответа
	 */
	public T getResponse()
	{
		return response;
	}
}
