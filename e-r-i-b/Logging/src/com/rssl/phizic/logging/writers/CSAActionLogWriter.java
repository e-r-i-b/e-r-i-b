package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.csaAction.CSAActionLogEntryBase;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Writer для действий в ЦСА идентифицированного пользователя.
 */
public interface CSAActionLogWriter
{
	/**
	 * Запись в журнал.
	 * @param entry - запись в журнале
	 */
	void write(CSAActionLogEntryBase entry) throws Exception;
}
