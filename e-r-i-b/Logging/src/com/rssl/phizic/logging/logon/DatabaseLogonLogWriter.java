package com.rssl.phizic.logging.logon;

/**
 * @author krenev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 */
public class DatabaseLogonLogWriter extends LogonLogWriterBase
{
	private static final LoggingJournalService loggingJournalService = new LoggingJournalService();

	protected void doSave(LogonLogEntry entry) throws Exception
	{
		loggingJournalService.add(entry);
	}
}
