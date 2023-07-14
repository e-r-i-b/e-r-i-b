package com.rssl.phizicgate.mdm.integration.mdm.message;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���� ����� jms
 */

public class Response<T>
{
	private final String messageId;
	private final String responseBody;
	private final T response;

	/**
	 * �����������
	 * @param messageId ������������� ���������
	 * @param responseBody ���� ������
	 * @param response ������ ������
	 */
	public Response(String messageId, String responseBody, T response)
	{
		this.messageId = messageId;
		this.responseBody = responseBody;
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
	 * @return ��������� ������������� ������
	 */
	public T getResponse()
	{
		return response;
	}
}
