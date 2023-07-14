package com.rssl.phizic.logging.push;

import com.rssl.phizic.logging.writers.StatisticLogWriter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Класс для вывод логируемых данных на консоль
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public class ConsloleStatisticLogWriter implements StatisticLogWriter
{
	private static final Log log = LogFactory.getLog(ConsloleStatisticLogWriter.class);

	public void write(StatisticLogEntry logEntry) throws Exception
	{
		log.info(logEntry);
	}
}
