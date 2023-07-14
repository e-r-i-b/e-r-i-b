package com.rssl.phizic.rsa.integration.jms;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.Constants;
import com.rssl.phizic.rsa.config.RSAConfig;
import com.rssl.phizic.utils.jms.JmsService;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_GATE;

/**
 * Вычитыватель сообщений из общей очереди шлюза к ВС ФМ
 *
 * @author khudyakov
 * @ created 15.06.15
 * @ $Author$
 * @ $Revision$
 */
public class JMSReceiver
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_GATE);
	private static final JmsService jmsService = new JmsService();

	private static final Queue queue;
	private static final QueueConnectionFactory factory;

	static
	{
		InitialContext ctx = null;

		try
		{
			ctx = new InitialContext();
			queue = (Queue) ctx.lookup(Constants.GENERAL_QUEUE_NAME);
			factory = (QueueConnectionFactory) ctx.lookup(Constants.GENERAL_QUEUE_FACTORY_NAME);
		}
		catch (NamingException e)
		{
			log.error(e.getMessage(), e);
			//noinspection ThrowInsideCatchBlockWhichIgnoresCaughtException
			throw new RuntimeException(e.getLocalizedMessage());
		}
		finally
		{
			if (ctx != null)
			{
				try { ctx.close(); } catch (NamingException ignored) {}
			}
		}
	}

	/**
	 * Получить текстовое сообщение из очереди с селектором и заданным таймаутом
	 * @param correlationId correlationId
	 * @return Текстовое сообщение их очереди. Если null - истек таймаут.
	 */
	public static TextMessage receiveMessageFormQueue(String correlationId) throws JMSException
	{
		return jmsService.receiveMessageFormQueue(queue, factory, new FraudMonitoringMessageSelector(correlationId).getSelector(), RSAConfig.getInstance().getJMSTimeOut());
	}
}
