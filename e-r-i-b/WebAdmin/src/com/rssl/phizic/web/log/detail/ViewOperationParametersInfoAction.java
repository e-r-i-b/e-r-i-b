package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.operations.GuestLogEntry;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.log.DownloadUserLogOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен просмотра информации об операции
 */

public class ViewOperationParametersInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		ViewOperationParametersInfoForm form = (ViewOperationParametersInfoForm)frm;
		DownloadUserLogOperation operation;
		if(checkAccess(DownloadUserLogOperation.class, "LogsService"))
			operation = createOperation(DownloadUserLogOperation.class, "LogsService");
		else if(checkAccess(DownloadUserLogOperation.class, "LogsServiceEmployee"))
			operation = createOperation(DownloadUserLogOperation.class, "LogsServiceEmployee");
		else if(checkAccess(DownloadUserLogOperation.class, "CommonLogService"))
			operation = createOperation(DownloadUserLogOperation.class, "CommonLogService");
		else if(checkAccess(DownloadUserLogOperation.class, "CommonLogServiceEmployee"))
			operation = createOperation(DownloadUserLogOperation.class, "CommonLogServiceEmployee");
		else if(checkAccess(DownloadUserLogOperation.class, "LogsServiceEmployeeUseClientForm"))
			operation = createOperation(DownloadUserLogOperation.class, "LogsServiceEmployeeUseClientForm");
		else
			operation = createOperation(DownloadUserLogOperation.class);
		operation.initialize(form.getId(), form.getType());

		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewOperationParametersInfoForm frm = (ViewOperationParametersInfoForm)form;
		DownloadUserLogOperation op = (DownloadUserLogOperation) operation;
		if (op.isGuest())
		{
			//сущность гостевого журнала
			frm.setGuestLogEntry((GuestLogEntry) operation.getEntity());
		}
		else
		{
			//сущность обычного журнала
			frm.setLogEntry((LogEntry) operation.getEntity());
		}

		frm.setFullName(op.getFullName());
	}
}