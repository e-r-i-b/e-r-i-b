package com.rssl.phizic.utils.jms;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author Erkin
 * @ created 21.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конструктор байтовых сообщений
 */
class BytesMessageCreator implements MessageCreator<BytesMessage>
{
	private static final BytesMessageCreator INSTANCE = new BytesMessageCreator();

	static BytesMessageCreator getInstance()
	{
		return INSTANCE;
	}

	public BytesMessage create(Session session, Serializable object) throws JMSException
	{
		if (!(object instanceof byte[]))
			throw new JMSException("Некорректный тип объекта " + object.getClass());

		BytesMessage bytesMessage = session.createBytesMessage();
		bytesMessage.writeBytes((byte[]) object);
		return bytesMessage;
	}
}
