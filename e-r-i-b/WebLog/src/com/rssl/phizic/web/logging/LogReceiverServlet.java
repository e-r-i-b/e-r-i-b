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
 * ������� ������� ������� ��������� ��������� ����������� �� jms.
 */
public class LogReceiverServlet extends LogReceiverServletBase
{
	protected List<MessageReceiverCreator> createReceivers(ServletConfig servletConfig)
	{
		List<MessageReceiverCreator> receiverCreators = new LinkedList<MessageReceiverCreator>();
		//��������� ���������� ��������� ���������� �������.
		receiverCreators.add(new SystemLogMessageReceiverCreator(servletConfig));

		//��������� ���������� ��������� ������� ���������.
		receiverCreators.add(new MessageLogMessageReceiverCreator(servletConfig));

		//��������� ���������� ��������� ������� �������� ������������.
		receiverCreators.add(new MessageReceiverCreator("operation-log", servletConfig));

		//��������� ���������� ��������� ������� ������ ������� ���.
		receiverCreators.add(new MessageReceiverCreator("csa-action-log", servletConfig));

		//��������� ���������� ��������� ������� ���������� ����������� ����������� � �������������� ������������
		receiverCreators.add(new MessageReceiverCreator("offer-notification-log", servletConfig));

		//��������� ���������� ��������� ������� ���������� ��� ���
		receiverCreators.add(new MessageReceiverCreator("quick-payment-panel-log", servletConfig));

		//��������� ���������� ��������� ������� ���������� ����������� ��������
		receiverCreators.add(new MessageReceiverCreator("advertising-log", servletConfig));

		//��������� ���������� ��������� �� ������ �������� ���������� ��������
		receiverCreators.add(new MessageReceiverCreator("filter-outcome-period-log", servletConfig));

		//��������� ���������� ���������  �� ��������� �������� ���������
		receiverCreators.add(new MessageReceiverCreator("card-operation-category-log", servletConfig));

		//��������� ���������� ��������� �� �������� ���������� �������
		receiverCreators.add(new MessageReceiverCreator("user-message-log", servletConfig));

		//��������� ���������� ��������� � ��������� �������� ����������
		receiverCreators.add(new MessageReceiverCreator("user-notification-log", servletConfig));

		//��������� ������ ���������� ��������� ��� �����, ������� ������������ ��������� �������� (��������, ������ � ���������� ������ �������� � ����/���  �� ������ ��������)
		receiverCreators.add(new MessageReceiverCreator("union-log", servletConfig));

		//��������� ���������� ��������.
		receiverCreators.add(new MessageReceiverCreator("monitoring-log", servletConfig));
		return receiverCreators;
	}

}

/**
 * ��������� ��������� ���� ��������� ��������.
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
 * ��������� ��������� ���� ���������.
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
