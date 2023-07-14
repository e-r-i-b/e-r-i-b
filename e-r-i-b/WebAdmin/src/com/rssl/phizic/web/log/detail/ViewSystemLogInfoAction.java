package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.log.DownloadSystemLogOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра информации о системном действии
 */

public class ViewSystemLogInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		DownloadSystemLogOperation operation = null;
		if(checkAccess(DownloadSystemLogOperation.class, "LogsService"))
			operation = createOperation(DownloadSystemLogOperation.class, "LogsService");
		else if(checkAccess(DownloadSystemLogOperation.class, "LogsServiceEmployee"))
			operation = createOperation(DownloadSystemLogOperation.class, "LogsServiceEmployee");
		else if(checkAccess(DownloadSystemLogOperation.class, "CommonLogServiceEmployeeUseClientForm"))
			operation = createOperation(DownloadSystemLogOperation.class, "CommonLogServiceEmployeeUseClientForm");
		else
			operation = createOperation(DownloadSystemLogOperation.class);

		operation.initialize(frm.getId());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewSystemLogInfoForm frm = (ViewSystemLogInfoForm)form;
		frm.setSystemLogEntry((SystemLogEntry)operation.getEntity());
	}
}