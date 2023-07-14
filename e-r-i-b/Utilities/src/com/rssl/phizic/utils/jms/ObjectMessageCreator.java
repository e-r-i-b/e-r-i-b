package com.rssl.phizic.utils.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;

/**
 * Конструктор jms object-сообщений
 *
 * @author gladishev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ObjectMessageCreator implements MessageCreator<ObjectMessage>
{
	private static final ObjectMessageCreator INSTANCE = new ObjectMessageCreator();

	public static ObjectMessageCreator getInstance()
	{
		return INSTANCE;
	}

	public ObjectMessage create(Session session, Serializable object) throws JMSException
	{
		return session.createObjectMessage(object);
	}
}
