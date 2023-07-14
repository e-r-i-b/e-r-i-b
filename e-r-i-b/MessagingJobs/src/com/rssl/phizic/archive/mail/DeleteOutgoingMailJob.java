package com.rssl.phizic.archive.mail;

import com.rssl.phizic.operations.mail.archive.ProcessMailOperationBase;
import com.rssl.phizic.operations.mail.archive.DeleteOutgoingMailOperation;
import org.quartz.JobExecutionException;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб для перемещения писем из списка входящих в список удаленных в АРМ сотрудника
 */
public class DeleteOutgoingMailJob extends ProcessMailJobBase
{

	public DeleteOutgoingMailJob() throws JobExecutionException
	{
		super();
	}

	protected ProcessMailOperationBase getProcessOperation()
	{
		return new DeleteOutgoingMailOperation();
	}
}
