package com.rssl.phizic.test.way4u.mdb;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.test.way4u.MessageProcessor;
import com.rssl.phizic.test.way4u.context.InitializerListener;
import com.rssl.phizicgate.way4u.messaging.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author krenev
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 * Заглушка - имитатор way4u JMSAdapter'а.
 * Данный адаптер слушает входящую очередь, обрабатывает запросы и перекладывает ответы в исходящую очередь.
 * При этом для "квитовки" сообщений идентификатор входящего сообщения(MsgId) устанавливает в качестве CorrelId исхощящего сообщения.
 */

public class MockJMSAdapter implements MessageDrivenBean, MessageListener
{
	private static final Log log = LogFactory.getLog(MockJMSAdapter.class);

	public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException
	{
	}

	public void ejbRemove() throws EJBException
	{
	}

	public void onMessage(Message message)
	{
		InitializerListener initializerListener = new InitializerListener();
		try
		{
			ApplicationInfo.setCurrentApplication(Application.TestApp);
			initializerListener.startTestServices();
			sendAnswer(message.getJMSMessageID(), MessageProcessor.getInstance().process(((TextMessage) message).getText()));
		}
		catch (Exception e)
		{
			log.error("Ошибка при обработке запроса", e);
		}
		finally
		{
			initializerListener.destroyTestServices();
		}
	}

	private void sendAnswer(String inputMessageId, String response) throws Exception
	{
		Context initialContext = null;
		QueueSender sender = null;
		QueueSession session = null;
		QueueConnection connection = null;
		try
		{
			initialContext = new InitialContext();
			QueueConnectionFactory outputQueueConnectionFactory = (QueueConnectionFactory) initialContext.lookup(Constants.OUTPUT_QUEUE_CONNECTION_FACTORY_NAME);

			connection = outputQueueConnectionFactory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			sender = session.createSender((Queue) initialContext.lookup(Constants.OUTPUT_QUEUE_NAME));
			TextMessage msg = session.createTextMessage();
			msg.setJMSCorrelationID(inputMessageId);
			msg.setText(response);
			sender.send(msg);
		}
		finally
		{
			if (sender != null)
				try{sender.close();}catch (Exception ignored){}
			if (session != null)
				try{session.close();}catch (Exception ignored){}
			if (connection != null)
				try{connection.close();}catch (Exception ignored){}
			if (initialContext != null)
				try{initialContext.close();}catch (Exception ignored){}
		}
	}
}
