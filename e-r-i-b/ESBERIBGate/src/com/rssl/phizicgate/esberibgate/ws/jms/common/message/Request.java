package com.rssl.phizicgate.esberibgate.ws.jms.common.message;

/**
 * @author akrenev
 * @ created 13.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Запрос в шину через jms
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
	 * конструктор
	 * @param messageId идентификатор сообщения
	 * @param messageType тип сообщения
	 * @param monitoringDocumentType тип сообщения для мониторинга
	 * @param message сообщение
	 * @param messageProcessor конструктор jms сообщений
	 * @param system система
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
	 * @return идентификатор jms сообщения
	 */
	public String getJmsMessageId()
	{
		return jmsMessageId;
	}

	/**
	 * задать идентификатор jms сообщения
	 * @param jmsMessageId идентификатор jms сообщения
	 */
	public void setJmsMessageId(String jmsMessageId)
	{
		this.jmsMessageId = jmsMessageId;
	}

	/**
	 * @return идентификатор сообщения
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * @return тип сообщения
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @return сообщение
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return целевая система
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * @return процессор jms ответов
	 */
	public MP getMessageProcessor()
	{
		return messageProcessor;
	}

	/**
	 * @return тип документа для мониторинга
	 */
	public String getMonitoringDocumentType()
	{
		return monitoringDocumentType;
	}
}
