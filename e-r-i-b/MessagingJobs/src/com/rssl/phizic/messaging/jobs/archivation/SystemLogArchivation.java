package com.rssl.phizic.messaging.jobs.archivation;

import org.quartz.JobExecutionException;
import com.rssl.phizic.operations.log.ArchiveLogOperationBase;
import com.rssl.phizic.operations.log.ArchiveSystemLogOperation;
import com.rssl.phizic.logging.Constants;

/**
 * @author eMakarov
 * @ created 13.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class SystemLogArchivation extends ArchivationJobBase
{
	public SystemLogArchivation() throws JobExecutionException
	{
		super(Constants.SYSTEM_LOG_PREFIX);
	}

	ArchiveLogOperationBase getOperation()
	{
		return new ArchiveSystemLogOperation();
	}

	public String getLogArchiveName()
	{
		return Constants.SYSTEM_LOG_ARCHIVE_NAME;
	}
}
