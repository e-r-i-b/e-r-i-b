package com.rssl.phizic.utils.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Конструктор jms text-сообщений
 *
 * @author gladishev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public class TextMessageCreator implements MessageCreator<TextMessage>
{
	private static final TextMessageCreator INSTANCE = new TextMessageCreator();

	public static TextMessageCreator getInstance()
	{
		return INSTANCE;
	}

	public TextMessage create(Session session, Serializable object) throws JMSException
	{
		if (!(object instanceof String))
		    throw new JMSException("Некорректный тип объекта " + object.getClass());

		return session.createTextMessage((String) object);
	}
}
