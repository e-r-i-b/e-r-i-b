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
 * XML-������ � ����
 */

public class ESBMessage<T> extends XmlMessage
{
	private final T object;
	private final Message original;

	/**
	 * �����������
	 * @param message �������� ���������
	 * @param object ��������� ������������� ���������
	 * @param original ���������
	 */
	public ESBMessage(String message, T object, Message original)
	{
		super(message, object.getClass(), null, null);
		this.object = object;
		this.original = original;
	}

	/**
	 * @return ������, ����������� ���������
	 */
	public T getObject()
	{
		return object;
	}

	/**
	 * @return ������������� ���������
	 */
	public String getMessageId()
	{
		try
		{
			return original.getJMSMessageID();
		}
		catch (JMSException e)
		{
			throw new InternalErrorException("������ ��������� �������� JMSMessageID", e);
		}
	}

	/**
	 * �������� �������� �������� �� ��������� ���������
	 * @param key ���� ��������
	 * @return ��������
	 */
	public String getProperty(String key)
	{
		try
		{
			return original.getStringProperty(key);
		}
		catch (JMSException e)
		{
			throw new InternalErrorException("������ ��������� �������� " + key, e);
		}
	}
}
