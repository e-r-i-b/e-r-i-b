package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 13.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���� ����� jms
 */

public class Response<T>
{
	private final String messageId;
	private final String responseBody;
	private final String errorCode;
	private final String errorMessage;
	private final T response;

	/**
	 * �����������
	 * @param messageId ������������� ���������
	 * @param responseBody ���� ������
	 * @param response ������ ������
	 * @param errorCode ��� ������
	 * @param errorMessage ����� ������
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
	 * @return ������������� ���������
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * @return ���� ������
	 */
	public String getResponseBody()
	{
		return responseBody;
	}

	/**
	 * @return ��� ������
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @return ����� ������
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * @return ��������� ������������� ������
	 */
	public T getResponse()
	{
		return response;
	}
}
