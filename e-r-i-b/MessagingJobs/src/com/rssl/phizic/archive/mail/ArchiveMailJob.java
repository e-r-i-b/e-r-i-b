package com.rssl.phizic.archive.mail;

import com.rssl.phizic.operations.mail.archive.ProcessMailOperationBase;
import com.rssl.phizic.operations.mail.archive.ArchiveDeletedMailOperation;
import org.quartz.JobExecutionException;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб для перемещения удаленных писем в архив.
 */
public class ArchiveMailJob extends ProcessMailJobBase
{
	public ArchiveMailJob() throws JobExecutionException
	{
		super();
	}

	protected ProcessMailOperationBase getProcessOperation()
	{
		return new ArchiveDeletedMailOperation();
	}
}
