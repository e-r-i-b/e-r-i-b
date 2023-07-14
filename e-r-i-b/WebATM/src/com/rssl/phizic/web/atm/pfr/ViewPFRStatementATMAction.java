package com.rssl.phizic.web.atm.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.pfr.PFRStatementBaseOperation;
import com.rssl.phizic.operations.pfr.ViewPFRStatementATMOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * Выписка из пенсионного фонда
 * @author Jatsky
 * @ created 22.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ViewPFRStatementATMAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		ViewPFRStatementATMOperation operation = createOperation(ViewPFRStatementATMOperation.class);
		String claimId = currentRequest().getParameter("claimId");
		if (claimId != null)
			operation.initialize(Long.valueOf(claimId));
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewPFRStatementATMForm frm = (ViewPFRStatementATMForm) form;
		ViewPFRStatementATMOperation op = (ViewPFRStatementATMOperation) operation;

		frm.setHtml(op.getHtml(PFRStatementBaseOperation.VIEW_MODE));
	}
}