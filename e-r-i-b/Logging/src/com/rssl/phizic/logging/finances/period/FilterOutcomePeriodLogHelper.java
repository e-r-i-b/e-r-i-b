package com.rssl.phizic.logging.finances.period;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.finances.ALFLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.FilterOutcomePeriodLogWriter;


/**
 * —ервис дл€ логировани€ выбора периода фильтрации расходов
 * @author Koptyaev
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */
public class FilterOutcomePeriodLogHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * «аписать лог фильтрации расходов
	 * @param record запись лога
	 */
	public static void writeEntryToLog(FilterOutcomePeriodLogRecord record)
	{
		try
		{
			if (!isLogFilterOutcomePeriodOn())
				return;

			writeToActionLog(record);
		}
		catch (Throwable t)
		{
			//падение логировани€ не должно нарушать работу системы.
			log.error(t);
		}
	}


	private static void writeToActionLog(FilterOutcomePeriodLogRecord entry)
	{
		if(entry == null)
			return;

		ALFLogConfig config = ConfigFactory.getConfig(ALFLogConfig.class);
		FilterOutcomePeriodLogWriter writer = config.getFilterOutcomePeriodLogWriter();
		try
		{
			if (writer != null)
			{
				writer.write(entry);
			}
		}
		catch (Throwable t)
		{
			//падение логировани€ не должно нарушать работу системы.
			log.error(t);
		}
	}

	/**
	 * @return включено ли логирование true - включено
	 */
	private static boolean isLogFilterOutcomePeriodOn()
	{
		return ConfigFactory.getConfig(IPSConfig.class).isLogFilterOutcomePeriodOn();
	}
}
