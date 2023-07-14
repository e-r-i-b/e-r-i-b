package com.rssl.phizic.web.client.pfr;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.pfr.PFRStatementBaseOperation;
import com.rssl.phizic.operations.pfr.ViewPFRStatementOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author Dorzhinov
 * @ created 07.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ViewPFRStatementAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException
	{
		ViewPFRStatementOperation operation = createOperation(ViewPFRStatementOperation.class);
		String claimId = currentRequest().getParameter("claimId");
		if(claimId != null)
			operation.initialize(Long.valueOf(claimId));
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ViewPFRStatementForm frm = (ViewPFRStatementForm)form;
		ViewPFRStatementOperation op = (ViewPFRStatementOperation)operation;

		frm.setHtml(op.getHtml(PFRStatementBaseOperation.VIEW_MODE));
		frm.setSNILS(op.getSNILS());
		frm.setClaimDate(op.getClaimDate());
		frm.setClaimId(op.getClaimId());
	}
}