package com.rssl.phizic.logging.settings;

import com.rssl.phizic.logging.push.PushEventType;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Запись лога об отправке сообщения клиенту
 * @author tisov
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */

public class UserMessageLogRecord implements Serializable
{
	private Long id;
	private Long loginId;
	private Calendar additionDate;
	private MessageType type;
	private String messageId;
	private PushEventType typeCode;

	/**
	 * геттер идентификатора записи
	 * @return - ид
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * сеттер идентификатора записи
	 * @param id - новый ид
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * геттер даты создания записи
	 * @return - дата создания
	 */
	public Calendar getAdditionDate()
	{
		return additionDate;
	}

	/**
	 * сеттер даты создания записи
	 * @param additionDate - новая дата создания
	 */
	public void setAdditionDate(Calendar additionDate)
	{
		this.additionDate = additionDate;
	}

	/**
	 * геттер типа сообщения
	 * @return - тип сообщения
	 */
	public MessageType getType()
	{
		return type;
	}

	/**
	 * сеттер типа сообщения
	 * @param type - новый тип сообщения
	 */
	public void setType(MessageType type)
	{
		this.type = type;
	}

	/**
	 * геттер идентификатора сообщения
	 * @return - ид сообщения
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * сеттер идентификатора сообщения
	 * @param messageId - новый ид сообщения
	 */
	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public PushEventType getTypeCode()
	{
		return typeCode;
	}

	/**
	 * сеттер для записи типа уведомления
	 * @param typeCode - новый типа уведомления
	 */
	public void setTypeCode(PushEventType typeCode)
	{
		this.typeCode = typeCode;
	}
}
