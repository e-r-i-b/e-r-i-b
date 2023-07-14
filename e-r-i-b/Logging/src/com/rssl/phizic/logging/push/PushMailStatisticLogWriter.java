package com.rssl.phizic.logging.push;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.MessageLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.StatisticLogWriter;

/**
 * Writer для записи логов push и mail уведомлений
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class PushMailStatisticLogWriter
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * логированвать push сообщение.
	 * @param entry - класс для логирования
	 */
	public void writeToPushWriter(StatisticLogEntry entry) throws Exception
	{
		try
		{
			StatisticLogWriter writer = getPushWriter();
			//если writer==null значит запись в журнал отключена
			if (writer != null)
				writer.write(entry);
		}
		catch (Exception me)
		{
			log.warn("Ошибка записи в журнал стаистики.", me);
			throw me;
		}
	}


	/**
	 * @return writer логирования push сообщений.
	 */
	private StatisticLogWriter getPushWriter()
	{
		return ConfigFactory.getConfig(MessageLogConfig.class).getPushStatisticWriter();
	}
}
