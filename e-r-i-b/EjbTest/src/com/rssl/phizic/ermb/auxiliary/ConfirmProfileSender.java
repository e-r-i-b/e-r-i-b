package com.rssl.phizic.ermb.auxiliary;

import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.synchronization.SOSMessageHelper;

import java.util.List;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

/**
 @author: EgorovaA
 @ created: 28.01.2013
 @ $Author$
 @ $Revision$
 */
class ConfirmProfileSender
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private Queue queue;
	private QueueConnectionFactory connectionFactory;

	ConfirmProfileSender()
	{
		try
		{
			InitialContext context = new InitialContext();
			connectionFactory = (QueueConnectionFactory)context.lookup(ErmbJndiConstants.ERMB_QCF);
			queue = (Queue) context.lookup(ErmbJndiConstants.ERMB_QUEUE);
		}
		catch (NamingException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	void send(List<ErmbUpdateProfileInfo> messages)
	{
		QueueSender sender = null;
		QueueSession session = null;
		QueueConnection connection = null;
		try
		{
			connection = connectionFactory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			sender = session.createSender(queue);

            String text = null;
            try
            {
                text = SOSMessageHelper.getConfirmProfileRqXml(messages);
            }
            catch (JAXBException e)
            {
               log.error(e.getMessage());
                return;
            }
            TextMessage message = session.createTextMessage(text);
			sender.send(message);
			//log.trace(text);

		}
		catch (JMSException e)
		{
			log.error("Не удалось отправить подтверждение об изменении профилей ", e);
		}
		finally
		{
			if (sender != null)
				try { sender.close(); } catch (JMSException ignored) {}
			if (session != null)
				try { session.close(); } catch (JMSException ignored) {}
			if (connection != null)
				try { connection.close(); } catch (JMSException ignored) {}
		}
	}
}
