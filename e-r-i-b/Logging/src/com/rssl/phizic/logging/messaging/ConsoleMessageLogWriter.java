package com.rssl.phizic.logging.messaging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author eMakarov
 * @ created 16.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConsoleMessageLogWriter extends MessageLogWriterBase
{
	private static final Log log = LogFactory.getLog(ConsoleMessageLogWriter.class);

	protected void doWrite(MessagingLogEntryBase logEntry) throws Exception
	{
		log.info(logEntry);
	}
}
