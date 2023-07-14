package com.rssl.phizic.logging.advertising;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.offerNotification.NotificationLogEntry;
import com.rssl.phizic.logging.offerNotification.OfferNotificationLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.AdvertisingLogWriter;
import com.rssl.phizic.logging.writers.OfferNotificationLogWriter;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static void addLogEntry(Long advertisingId, AdvertisingLogEntryType type) throws Exception
	{
		try
		{
			if (advertisingId == null)
				return;

			AdvertisingLogEntry logEntry = new AdvertisingLogEntry();
			logEntry.setAdvertisingId(advertisingId);
			logEntry.setDate(Calendar.getInstance());
			logEntry.setType(type);
			writeToLog(logEntry);
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}

	private static void writeToLog(AdvertisingLogEntry entry)
	{
		if(entry == null)
			return;

		AdvertisingLogConfig config = ConfigFactory.getConfig(AdvertisingLogConfig.class);
		if (!config.isLoggingOn())
			return;
		AdvertisingLogWriter writer = config.getWriter();
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
