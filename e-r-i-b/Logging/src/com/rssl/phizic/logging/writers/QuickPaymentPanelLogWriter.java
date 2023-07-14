package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.quick.pay.QuickPaymentPanelLogEntry;

/**
 *  Writer для записи логов для статистики по ПБО
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface QuickPaymentPanelLogWriter
{
	/**
	 * записать сообщение
	 * @param entry сообщение
	 */
	void write(QuickPaymentPanelLogEntry entry) throws Exception;
}
