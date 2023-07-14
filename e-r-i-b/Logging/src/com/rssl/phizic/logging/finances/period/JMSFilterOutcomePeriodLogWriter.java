package com.rssl.phizic.logging.finances.period;

import com.rssl.phizic.logging.jms.JMSQueueSender;
import com.rssl.phizic.logging.writers.FilterOutcomePeriodLogWriter;

import javax.jms.JMSException;

/**
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 * Writer для асинхронного логирования записей о выборе периода фильтрации расходов.
 */
public class JMSFilterOutcomePeriodLogWriter  implements FilterOutcomePeriodLogWriter
{
	public static final String FACTORY_NAME = "jms/ERIBLoggingQueueConnectionFactory";
	public static final String QUEUE_NAME = "jms/ERIBFilterOutcomePeriodLogQueue";
	private JMSQueueSender sender;

	public void write(FilterOutcomePeriodLogRecord entry) throws JMSException
	{
		if(sender == null)
		{
			sender = new JMSQueueSender(FACTORY_NAME, QUEUE_NAME);
		}
		sender.send(entry);
	}
}
