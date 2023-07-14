package com.rssl.phizic.web.common.background;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.background.ViewBackgroundTaskOperationBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author krenev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ViewBackgroundTaskAction extends ViewActionBase
{
	protected ViewBackgroundTaskOperationBase createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		ViewBackgroundTaskOperationBase operation = createOperation(getCurrentMapping().getParameter());
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewBackgroundTaskForm frm = (ViewBackgroundTaskForm) form;
		ViewBackgroundTaskOperationBase op = (ViewBackgroundTaskOperationBase) operation;
		frm.setTask(op.getEntity());
	}
}
