package com.rssl.phizic.web.logging;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.jms.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 26.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * Сервлет запуска потоков получения сообщений логирования из jms.
 */
public class LogReceiverServlet extends LogReceiverServletBase
{
	protected List<MessageReceiverCreator> createReceivers(ServletConfig servletConfig)
	{
		List<MessageReceiverCreator> receiverCreators = new LinkedList<MessageReceiverCreator>();
		//запускаем получателя сообщений системного журнала.
		receiverCreators.add(new SystemLogMessageReceiverCreator(servletConfig));

		//запускаем получателя сообщений журнала сообщений.
		receiverCreators.add(new MessageLogMessageReceiverCreator(servletConfig));

		//запускаем получателя сообщений журнала действий пользователя.
		receiverCreators.add(new MessageReceiverCreator("operation-log", servletConfig));

		//запускаем получателя сообщений журнала входов клиента ЦСА.
		receiverCreators.add(new MessageReceiverCreator("csa-action-log", servletConfig));

		//запускаем получателя сообщений журнала статистики отображений уведомлений о предодобренных предложениях
		receiverCreators.add(new MessageReceiverCreator("offer-notification-log", servletConfig));

		//запускаем получателя сообщений журнала статистики для ПБО
		receiverCreators.add(new MessageReceiverCreator("quick-payment-panel-log", servletConfig));

		//запускаем получателя сообщений журнала статистики отображений баннеров
		receiverCreators.add(new MessageReceiverCreator("advertising-log", servletConfig));

		//запускаем получателя сообщений по выбору периодов фильтрации расходов
		receiverCreators.add(new MessageReceiverCreator("filter-outcome-period-log", servletConfig));

		//запускаем получателя сообщений  по изменению клиентом категорий
		receiverCreators.add(new MessageReceiverCreator("card-operation-category-log", servletConfig));

		//запускаем получателя сообщений об отправке оповещений клиенту
		receiverCreators.add(new MessageReceiverCreator("user-message-log", servletConfig));

		//запускаем получателя сообщений о изменении настроек оповещений
		receiverCreators.add(new MessageReceiverCreator("user-notification-log", servletConfig));

		//запускаем общего получателя сообщений для логов, которые предполагают небольшую нагрузку (например, записи о превышении лимита запросов в ЕРМБ/МБК  по номеру телефона)
		receiverCreators.add(new MessageReceiverCreator("union-log", servletConfig));

		//запускаем мониторинг операций.
		receiverCreators.add(new MessageReceiverCreator("monitoring-log", servletConfig));
		return receiverCreators;
	}

}

/**
 * Создатель слушателя лога системных действий.
 */
class SystemLogMessageReceiverCreator extends MessageReceiverCreator
{
	SystemLogMessageReceiverCreator(ServletConfig servletConfig)
	{
		super("system-log", servletConfig);
	}

	@Override protected JMSReceiverTreadBase generateReceiver(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		return new JMSSystemLogReceiverTread(timeout, batchSize, flushTryCount, queueName, queueFactoryName, dbInstanceName);
	}
}

/**
 * Создатель слушателя лога сообщений.
 */
class MessageLogMessageReceiverCreator extends MessageReceiverCreator
{
	MessageLogMessageReceiverCreator(ServletConfig servletConfig)
	{
		super("message-log", servletConfig);
	}

	@Override protected JMSReceiverTreadBase generateReceiver(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		return new JMSMessageLogReceiverTreadBase(timeout, batchSize, flushTryCount, queueName, queueFactoryName, dbInstanceName);
	}
}
