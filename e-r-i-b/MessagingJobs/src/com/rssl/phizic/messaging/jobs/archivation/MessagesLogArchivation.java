package com.rssl.phizic.messaging.jobs.archivation;

import org.quartz.JobExecutionException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.ArchiveLogOperationBase;
import com.rssl.phizic.operations.log.ArchiveMessagesLogOperation;

/**
 * @author eMakarov
 * @ created 14.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class MessagesLogArchivation extends ArchivationJobBase
{
	public MessagesLogArchivation() throws JobExecutionException
	{
		super(Constants.MESSAGE_LOG_PREFIX);
	}

	ArchiveLogOperationBase getOperation()
	{
		return new ArchiveMessagesLogOperation();
	}

	public String getLogArchiveName()
	{
		return Constants.MESSAGE_LOG_ARCHIVE_NAME;
	}
}
