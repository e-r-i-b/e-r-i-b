package com.rssl.phizic.web.jms;

import com.rssl.phizic.utils.StringHelper;

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
public abstract class LogReceiverServletBase extends HttpServlet
{
	private List<MultiThreadJMSReceiver> logReceivers = new LinkedList<MultiThreadJMSReceiver>();

	protected abstract List<MessageReceiverCreator> createReceivers(ServletConfig servletConfig);

	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);

		try
		{
			List<MessageReceiverCreator> receiverCreators = createReceivers(servletConfig);
			for (MessageReceiverCreator creator : receiverCreators)
			{
				MultiThreadJMSReceiver receiver = creator.create();
				if (receiver!= null)
				{
					receiver.start();
					logReceivers.add(receiver);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			stopReceivers();
			throw new ServletException(e);
		}
	}

	public void destroy()
	{
		stopReceivers();
		super.destroy();
	}

	private void stopReceivers()
	{
		for (MultiThreadJMSReceiver receiver : logReceivers)
		{
			if (receiver == null)
				continue;

			receiver.stop();
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}




