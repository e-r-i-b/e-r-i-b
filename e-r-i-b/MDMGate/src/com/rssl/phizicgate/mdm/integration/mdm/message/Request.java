package com.rssl.phizicgate.mdm.integration.mdm.message;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ � ���� ����� jms
 */

public class Request<MP extends MessageProcessor<MP>>
{
	private final String messageId;
	private final String messageType;
	private final String message;
	private final String system;
	private final MP messageProcessor;

	/**
	 * �����������
	 * @param messageId ������������� ���������
	 * @param messageType ��� ���������
	 * @param message ���������
	 * @param messageProcessor ����������� jms ���������
	 * @param system �������
	 */
	public Request(String messageId, String messageType, String message, MP messageProcessor, String system)
	{
		this.messageId = messageId;
		this.messageType = messageType;
		this.message = message;
		this.messageProcessor = messageProcessor;
		this.system = system;
	}

	/**
	 * @return ������������� ���������
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * @return ��� ���������
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @return ���������
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return ������� �������
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * @return ��������� jms �������
	 */
	public MP getMessageProcessor()
	{
		return messageProcessor;
	}
}
