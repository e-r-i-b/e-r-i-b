package com.rssl.phizic.ejbtest.service;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.jms.MessageCreator;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author komarov
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class ESBMessageTestCreator implements MessageCreator<TextMessage>
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);


	private static final String OUT_QUEUE_NAME = "jms/esb/esbQueue";


	private static final ESBMessageTestCreator INSTANCE = new ESBMessageTestCreator();

	public static ESBMessageTestCreator getInstance()
	{
		return INSTANCE;
	}

	private void setReplyTo(TextMessage message, String name)  throws JMSException
	{
		try
		{
			InitialContext ctx = new InitialContext();
			Queue queue = (Queue) ctx.lookup(name);
			message.setJMSReplyTo(queue);
		}
		catch (NamingException ne)
		{
			log.error(ne);
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new JMSException(ne.getLocalizedMessage());
		}
	}


	public TextMessage create(Session session, Serializable object) throws JMSException
	{
		if (!(object instanceof String))
			throw new JMSException("Некорректный тип объекта " + object.getClass());

		TextMessage message =  session.createTextMessage((String) object);
		setReplyTo(message, OUT_QUEUE_NAME);
		return message;
	}
}
