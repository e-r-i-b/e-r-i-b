package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.advertising.AdvertisingLogEntry;

/**
 *  Writer для записи логов для статистики по баннерам
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface AdvertisingLogWriter
{
	/**
	 * записать сообщение
	 * @param entry сообщение
	 */
	void write(AdvertisingLogEntry entry) throws Exception;
}
