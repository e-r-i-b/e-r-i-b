package com.rssl.phizic.monitoring.fraud.messages;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.rsa.exceptions.AppendNotificationException;
import com.rssl.phizic.rsa.notifications.FraudNotification;
import com.rssl.phizic.rsa.notifications.FraudNotificationService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * @author tisov
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 * Обработчик JMS-сообщений с фрод-оповещениями
 */
public class NotificationTransportMessageProcessor
{
	private TextMessage message;

	/**
	 * @param message - сообщение
	 */
	public NotificationTransportMessageProcessor(Message message)
	{
		this.message = (TextMessage) message;
	}

	/**
	 * Обработать сообщение
	 */
	public void process() throws BusinessException
	{
		try
		{
			FraudNotification notification = new FraudNotification(message.getText());
			FraudNotificationService.getInstance().save(notification);
		}
		catch (JMSException e)
		{
			throw new BusinessException(e);
		}
		catch (AppendNotificationException e)
		{
			throw new BusinessException(e);
		}
	}
}
