package com.rssl.phizicgate.mdm.integration.mdm.message;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Запрос в шину через jms
 */

public class Request<MP extends MessageProcessor<MP>>
{
	private final String messageId;
	private final String messageType;
	private final String message;
	private final String system;
	private final MP messageProcessor;

	/**
	 * конструктор
	 * @param messageId идентификатор сообщения
	 * @param messageType тип сообщения
	 * @param message сообщение
	 * @param messageProcessor конструктор jms сообщений
	 * @param system система
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
}
