package com.rssl.phizic.web.logging;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.exceptions.ExceptionEntryService;
import com.rssl.phizic.logging.system.ExceptionSystemLogEntry;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.jms.JMSReceiverTreadBase;
import org.hibernate.Session;

/**
 * @author osminin
 * @ created 18.03.15
 * @ $Author$
 * @ $Revision$
 */
public class JMSSystemLogReceiverTread extends JMSReceiverTreadBase
{
	/**
	 * ctor
	 * @param timeout Таймаут ожидания
	 * @param batchSize Размер паки сообщений
	 * @param flushTryCount Количество попыток сброса сообщений в БД
	 * @param queueName наименование очереди
	 * @param queueFactoryName наименование фабрики очереди
	 * @param dbInstanceName инстанс БД
	 */
	public JMSSystemLogReceiverTread(long timeout, int batchSize, int flushTryCount, String queueName, String queueFactoryName, String dbInstanceName)
	{
		super(timeout, batchSize, flushTryCount, queueName, queueFactoryName, dbInstanceName);
	}

	@Override
	protected void storeMessage(Session session, Object message) throws Exception
	{
		ApplicationInfo.setCurrentApplication(Application.WebLog);

		try
		{
			if (message instanceof ExceptionSystemLogEntry)
			{
				ExceptionSystemLogEntry entry = (ExceptionSystemLogEntry) message;
				ExceptionEntryService service = new ExceptionEntryService();

				service.update(entry);

				if (StringHelper.isNotEmpty(entry.getPhoneNumber()))
				{
					super.storeMessage(session, new GuestSystemLogEntry(entry));
				}
				else if (entry.getLevel() != null)
				{
					super.storeMessage(session, new SystemLogEntry(entry));
				}
			}
			else
			{
				super.storeMessage(session, message);
			}
		}
		finally
		{
			ApplicationInfo.setDefaultApplication();
		}
	}
}
