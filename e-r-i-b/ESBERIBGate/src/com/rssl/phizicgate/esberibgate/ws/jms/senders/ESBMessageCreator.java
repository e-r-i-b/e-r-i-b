package com.rssl.phizicgate.esberibgate.ws.jms.senders;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.MessageCreator;


import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 24.04.2014
 * @ $Author$
 * @ $Revision$
 */
public class ESBMessageCreator implements MessageCreator<TextMessage>
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);


	private static final String OUT_QUEUE_NAME = "jms/esb/esbOutQueue";


	private static final ESBMessageCreator INSTANCE = new ESBMessageCreator();

	public static ESBMessageCreator getInstance()
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
		setGroupId(message);
		return message;
	}

	private void setGroupId(TextMessage message) throws JMSException
	{
		String groupId = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getGroupId();
		if(StringHelper.isNotEmpty(groupId))
		{
			message.setStringProperty("JMSXGroupID", groupId);
			message.setBooleanProperty("JMS_IBM_Last_Msg_In_Group", true);
		}
	}
}
