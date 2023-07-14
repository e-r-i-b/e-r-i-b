package com.rssl.phizic.test.externalSystem.monitoring;

import org.apache.commons.lang.ArrayUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * @author akrenev
 * @ created 26.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Менеджер потоков мониторинга
 */

public class MonitoringESRequestTreadManager
{
	private final int threadCount;
	private final ExecutorService executor;
	private final MonitoringESRequestJMSReceiverTread[] receiverTreads;

	private static final MonitoringESRequestTreadManager instance = new MonitoringESRequestTreadManager();

	private MonitoringESRequestTreadManager()
	{
		threadCount = Config.getInstance().getThreadCount();
		executor = Executors.newFixedThreadPool(threadCount);
		receiverTreads = new MonitoringESRequestJMSReceiverTread[threadCount];
	}

	static MonitoringESRequestTreadManager getInstance()
	{
		return instance;
	}

	void run(long timeout, String queueName, String queueFactoryName) throws NamingException, JMSException
	{
		for (int i = 0; i < threadCount; i++)
		{
			MonitoringESRequestJMSReceiverTread receiverTread = new MonitoringESRequestJMSReceiverTread(timeout, queueName, queueFactoryName);
			receiverTreads[i] = receiverTread;
			run(receiverTread);
		}
	}

	void stop()
	{
		if (ArrayUtils.isEmpty(receiverTreads))
			return;

		for (MonitoringESRequestJMSReceiverTread receiver : receiverTreads)
		{
			if (receiver!= null)
			{
				receiver.stop();
			}
		}
	}

	private void run(MonitoringESRequestJMSReceiverTread tread) throws NamingException, JMSException
	{
		tread.start();
		executor.execute(tread);
	}
}
