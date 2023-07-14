package com.rssl.phizic.messaging.jobs.archivation;

import org.quartz.JobExecutionException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.ArchiveLogOperationBase;
import com.rssl.phizic.operations.log.ArchiveMessagesLogOperation;
import com.rssl.phizic.operations.log.ArchiveEntriesLogOperation;

/**
 * @author komarov
 * @ created 12.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class EntriesLogArchivation extends ArchivationJobBase
{
	public EntriesLogArchivation() throws JobExecutionException
	{
		super(Constants.ENTRIES_LOG_PREFIX);
	}

	ArchiveLogOperationBase getOperation()
	{
		return new ArchiveEntriesLogOperation();
	}

	public String getLogArchiveName()
	{
		return Constants.ENTRIES_LOG_ARCHIVE_NAME;
	}
}
