package com.rssl.phizic.web.jms;

import com.rssl.phizic.utils.StringHelper;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;

/**
 * ��������� ��������� ���������� �������.
 */
public class MessageReceiverCreator
{
	//������������ ����� �������� ���������. �� ���������� ����� ������� ��� ����������� ��������� ������������ � ��.
	private static final int DEFAULT_MAX_WAIT_TIME = 1000;
	//������ ����� ��������� ��� ���������� �������
	private static final int DEFAULT_PACK_SIZE = 100;
	//���������� ������� ������ ��������� � ��. �� ��������� ���������� ���� ������� ��������� ��������.
	private static final int DEFAULT_FLUSH_TRY_COUNT = 1;
	//���������� ������� ������ �������.
	private static final int DEFAULT_THREADS_COUNT = 1;

	private String logName;
	private ServletConfig servletConfig;

	public MessageReceiverCreator(String logName, ServletConfig servletConfig)
	{
		this.logName = logName;
		this.servletConfig = servletConfig;
	}

	/**
	 * ������� ���������.
	 *
	 * @throws javax.jms.JMSException
	 * @throws javax.naming.NamingException
	 */
	MultiThreadJMSReceiver create() throws JMSException, NamingException
	{
		if (!Boolean.valueOf(servletConfig.getInitParameter("receive-" + logName)))
			return null;

		final int timeout = getIntInitalParameter(servletConfig, logName + "-timeout", DEFAULT_MAX_WAIT_TIME);
		final int batchSize = getIntInitalParameter(servletConfig, logName + "-pack-size", DEFAULT_PACK_SIZE);
		final int flushTryCount = getIntInitalParameter(servletConfig, logName + "-flush-try-count", DEFAULT_FLUSH_TRY_COUNT);
		int threadsCount = getIntInitalParameter(servletConfig, logName + "-threads-count", DEFAULT_THREADS_COUNT);
		final String dbInstanceName = servletConfig.getInitParameter("receive-" + logName + "-dbinstance-name");
		final String queueName = servletConfig.getInitParameter("receive-" + logName + "-queue-name");
		final String queueFactory = servletConfig.getInitParameter("receive-" + logName + "-queue-factory-name");
		return new MultiThreadJMSReceiver(threadsCount)
		{
			public JMSReceiverTreadBase createReceiver()
			{
				return generateReceiver(timeout, batchSize, flushTryCount, queueName, queueFactory, dbInstanceName);
			}
		};
	}

	/**
	 * ������� ����������� ���������.
	 *
	 * @param timeout timeout
	 * @param batchSize batchSize
	 * @param flushTryCount flushTryCount
	 * @param queueName queueName
	 * @param queueFactoryName queueFactoryName
	 * @param dbInstanceName dbInstanceName
	 * @return ��������� ��� ���������� ���������.
	 */
	protected JMSReceiverTreadBase generateReceiver(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		return new JMSReceiverTreadBase(timeout, batchSize, flushTryCount, queueName, queueFactoryName, dbInstanceName);
	}

	private int getIntInitalParameter(ServletConfig servletConfig, String parameterName, int defaultValue)
	{
		String parameter = servletConfig.getInitParameter(parameterName);
		if (StringHelper.isEmpty(parameter))
		{
			return defaultValue;
		}
		return Integer.valueOf(parameter);
	}
}