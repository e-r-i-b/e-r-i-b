package com.rssl.phizic.logging.offerNotification;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.OfferNotificationLogWriter;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 31.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class OfferNotificationLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	public static void writeEntryToLog(Long notificationId, String notificationName, NotificationLogEntryType type)
	{
		try
		{
			NotificationLogEntry logEntry = new NotificationLogEntry();
			logEntry.setNotificationId(notificationId);
			logEntry.setName(notificationName);
			logEntry.setDate(Calendar.getInstance());
			logEntry.setLoginId(LogThreadContext.getLoginId());
			logEntry.setType(type);
			writeToActionLog(logEntry);
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}

	private static void writeToActionLog(NotificationLogEntry entry)
	{
		if(entry == null)
			return;

		OfferNotificationLogConfig config = ConfigFactory.getConfig(OfferNotificationLogConfig.class);
		if (!config.isLoggingOn())
			return;
		OfferNotificationLogWriter writer = config.getWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}
}
