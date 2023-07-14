package com.rssl.phizic.web.client.loans.loan;

import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.loans.loan.GetLoanInfoOperation;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.BusinessException;

/**
 * @author gladishev
 * @ created 28.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoanDetailsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException
	{
		LoanDetailsForm frm = (LoanDetailsForm) form;
		GetLoanInfoOperation operation = createOperation(GetLoanInfoOperation.class);
		operation.initialize(frm.getLoanId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		GetLoanInfoOperation op = (GetLoanInfoOperation) operation;
		LoanDetailsForm frm = (LoanDetailsForm) form;
		frm.setLoan(op.getEntity());
		frm.setScheduleItem(op.getScheduleItem());
		frm.setSchedule(op.getSchedule());
		frm.setPerson(op.getPerson());
	}
}
