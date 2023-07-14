package com.rssl.phizic.web.basket;

import com.rssl.phizic.BasketPaymentsListenerConfig;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.jms.MultiThreadJMSReceiver;
import org.hibernate.util.StringHelper;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 16.05.14
 * @ $Author$
 * @ $Revision$
 * Сервлет - слушатель очередей от АС "AutoPay" для корзины платежей.
 */
public class BasketReceiverServlet extends HttpServlet
{
	private MultiThreadJMSReceiver addBillBasketInfoReceiver;
	private MultiThreadJMSReceiver acceptBillBasketExecuteReceiver;
	//Максимальное время ожидания сообщения. по прошествии этого времени все накопленные сообщения обрабатываются.
	private static final int DEFAULT_MAX_WAIT_TIME = 1000;
	//размер пачки сообщений для группового коммита
	private static final int DEFAULT_PACK_SIZE = 1;
	//Количество попыток обработки. по истечении количества этих попыток сообщения теряются.
	private static final int DEFAULT_FLUSH_TRY_COUNT = 1;
	//количество потоков опроса очереди.
	private static final int DEFAULT_THREADS_COUNT = 1;

	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);
		ApplicationInfo.setCurrentApplication(Application.BasketProxyListener);
		BasketPaymentsListenerConfig config = ConfigFactory.getConfig(BasketPaymentsListenerConfig.class);
		try
		{
			//слушатель очереди от АС "AutoPay" Q.IN
			if (Boolean.valueOf(servletConfig.getInitParameter("receive-addBillBasketInfo")))
			{
				final int timeout = getIntInitialParameter(servletConfig, "addBillBasketInfo-timeout", DEFAULT_MAX_WAIT_TIME);
				final String queueName = config.getAddBillBasketInfoInQueue();
				final String queueFactory = config.getAddBillBasketInfoInQueueFactory();
				addBillBasketInfoReceiver = new MultiThreadJMSReceiver(DEFAULT_THREADS_COUNT)   //значение всегда должно быть 1! Реализация однопоточки.
				{
					public JMSAddBillBasketInfoReceiverTread createReceiver()
					{
						return new JMSAddBillBasketInfoReceiverTread(timeout, DEFAULT_PACK_SIZE, DEFAULT_FLUSH_TRY_COUNT, queueName, queueFactory);
					}
				};
				addBillBasketInfoReceiver.start();
			}
			//слушатель очереди Q.RESPONSE от АС "AutoPay"
			if (Boolean.valueOf(servletConfig.getInitParameter("receive-acceptBillBasketExecute")))
			{
				//запускаем получателя сообщений о принятии акцепта оплаты.
				final int timeout = getIntInitialParameter(servletConfig, "acceptBillBasketExecute-timeout", DEFAULT_MAX_WAIT_TIME);
				int threadsCount = getIntInitialParameter(servletConfig, "acceptBillBasketExecute-threads-count", DEFAULT_THREADS_COUNT);
				final String queueName = config.getAcceptBillBasketExecuteInQueue();
				final String queueFactory = config.getAcceptBillBasketExecuteInQueueFactory();
				acceptBillBasketExecuteReceiver = new MultiThreadJMSReceiver(threadsCount)
				{
					public JMSAcceptBillBasketExecuteReceiverTread createReceiver()
					{
						return new JMSAcceptBillBasketExecuteReceiverTread(timeout, DEFAULT_PACK_SIZE, DEFAULT_FLUSH_TRY_COUNT, queueName, queueFactory);
					}
				};
				acceptBillBasketExecuteReceiver.start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace(System.out);
			stopReceivers();
			throw new ServletException(e);
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}

	private int getIntInitialParameter(ServletConfig servletConfig, String parameterName, int defaultValue)
	{
		String parameter = servletConfig.getInitParameter(parameterName);
		if (StringHelper.isEmpty(parameter))
		{
			return defaultValue;
		}
		return Integer.valueOf(parameter);
	}

	public void destroy()
	{
		stopReceivers();
		super.destroy();
	}

	private void stopReceivers()
	{
		if (addBillBasketInfoReceiver != null)
		{
			addBillBasketInfoReceiver.stop();
		}
		if (acceptBillBasketExecuteReceiver != null)
		{
			acceptBillBasketExecuteReceiver.stop();
		}
	}

	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
