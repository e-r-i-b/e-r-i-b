package com.rssl.phizic.esb.ejb.mock;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.messaging.XmlMessage;

import javax.jms.JMSException;
import javax.jms.Message;

/**
 * @author akrenev
 * @ created 26.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * XML-запрос в шину
 */

public class ESBMessage<T> extends XmlMessage
{
	private final T object;
	private final Message original;

	/**
	 * конструктор
	 * @param message исходное сообщение
	 * @param object объектное представление сообщения
	 * @param original сообщение
	 */
	public ESBMessage(String message, T object, Message original)
	{
		super(message, object.getClass(), null, null);
		this.object = object;
		this.original = original;
	}

	/**
	 * @return объект, описывающий сообщение
	 */
	public T getObject()
	{
		return object;
	}

	/**
	 * @return идентификатор сообщения
	 */
	public String getMessageId()
	{
		try
		{
			return original.getJMSMessageID();
		}
		catch (JMSException e)
		{
			throw new InternalErrorException("Ошибка получения свойства JMSMessageID", e);
		}
	}

	/**
	 * получить значение свойства из исходного сообщения
	 * @param key ключ свойства
	 * @return значение
	 */
	public String getProperty(String key)
	{
		try
		{
			return original.getStringProperty(key);
		}
		catch (JMSException e)
		{
			throw new InternalErrorException("Ошибка получения свойства " + key, e);
		}
	}
}
