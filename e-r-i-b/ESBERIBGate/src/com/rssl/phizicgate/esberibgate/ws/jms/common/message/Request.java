package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 13.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ � ���� ����� jms
 */

public class Request<MP extends MessageProcessor<MP>>
{
	public static final String SKIP_MONITORING = "";

	private String jmsMessageId;
	private final String messageId;
	private final String messageType;
	private final String monitoringDocumentType;
	private final String message;
	private final String system;
	private final MP messageProcessor;

	/**
	 * �����������
	 * @param messageId ������������� ���������
	 * @param messageType ��� ���������
	 * @param monitoringDocumentType ��� ��������� ��� �����������
	 * @param message ���������
	 * @param messageProcessor ����������� jms ���������
	 * @param system �������
	 */
	public Request(String messageId, String messageType, String monitoringDocumentType, String message, MP messageProcessor, String system)
	{
		this.messageId = messageId;
		this.messageType = messageType;
		this.monitoringDocumentType = monitoringDocumentType;
		this.message = message;
		this.messageProcessor = messageProcessor;
		this.system = system;
	}

	/**
	 * @return ������������� jms ���������
	 */
	public String getJmsMessageId()
	{
		return jmsMessageId;
	}

	/**
	 * ������ ������������� jms ���������
	 * @param jmsMessageId ������������� jms ���������
	 */
	public void setJmsMessageId(String jmsMessageId)
	{
		this.jmsMessageId = jmsMessageId;
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

	/**
	 * @return ��� ��������� ��� �����������
	 */
	public String getMonitoringDocumentType()
	{
		return monitoringDocumentType;
	}
}
