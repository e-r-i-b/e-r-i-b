package com.rssl.phizic.archive.mail;

import org.quartz.JobExecutionException;
import com.rssl.phizic.operations.mail.archive.ProcessMailOperationBase;
import com.rssl.phizic.operations.mail.archive.DeleteIncomingMailOperation;

/**
 * @author mihaylov
 * @ created 24.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Джоб для перемещения писем из списка входящих в список удаленных в АРМ сотрудника 
 */
public class DeleteIncomingMailJob extends ProcessMailJobBase
{
	public DeleteIncomingMailJob() throws JobExecutionException
	{
		super();
	}

	protected ProcessMailOperationBase getProcessOperation()
	{
		return new DeleteIncomingMailOperation();
	}
}
