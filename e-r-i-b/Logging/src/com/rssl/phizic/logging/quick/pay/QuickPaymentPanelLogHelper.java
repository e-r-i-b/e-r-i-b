package com.rssl.phizic.logging.quick.pay;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.QuickPaymentPanelLogWriter;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class QuickPaymentPanelLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public static void addLogEntry(Long blokId, PanelLogEntryType type, Money amount, String tbNumber) throws Exception
	{
		try
		{
			if (blokId == null)
				return;

			QuickPaymentPanelLogEntry logEntry = new QuickPaymentPanelLogEntry();
			logEntry.setQuickPaymentPanelBlokId(blokId);
			logEntry.setTime(Calendar.getInstance());
			logEntry.setType(type);
			logEntry.setTbNumber(tbNumber);
			logEntry.setAmount(amount == null ? null : amount.getDecimal());
			writeToLog(logEntry);
		}
		catch (Throwable t)
		{
			//падение логирования не должно нарушать работу системы.
			log.error(t);
		}
	}
	private static void writeToLog(QuickPaymentPanelLogEntry entry)
	{
		if(entry == null)
			return;

		QuickPaymentPanelLogConfig config = ConfigFactory.getConfig(QuickPaymentPanelLogConfig.class);
		if (!config.isLoggingOn())
			return;
		QuickPaymentPanelLogWriter writer = config.getWriter();
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
