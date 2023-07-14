package com.rssl.phizic.messaging.jobs.archivation;

import org.quartz.JobExecutionException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.operations.log.ArchiveLogOperationBase;
import com.rssl.phizic.operations.log.ArchiveOperationsLogOperation;

/**
 * @author eMakarov
 * @ created 14.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class OperationsLogArchivation extends ArchivationJobBase
{
	public OperationsLogArchivation() throws JobExecutionException
	{
		super(Constants.USER_LOG_PREFIX);
	}

	ArchiveLogOperationBase getOperation()
	{
		return new ArchiveOperationsLogOperation();
	}

	public String getLogArchiveName()
	{
		return Constants.USER_LOG_ARCHIVE_NAME;
	}
}
