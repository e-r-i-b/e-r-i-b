package com.rssl.phizic.web.client.deposits;

import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.print.DepositInfoPrintOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Novikov_A
 * Date: 28.12.2006
 * Time: 18:51:19
 */
public class PrintDepositInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintDepositInfoForm frm = (PrintDepositInfoForm) form;
		DepositInfoPrintOperation operation = createOperation(DepositInfoPrintOperation.class);
		operation.initialize(Long.valueOf(frm.getDepositId()));
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		DepositInfoPrintOperation op = (DepositInfoPrintOperation) operation;
		PrintDepositInfoForm frm = (PrintDepositInfoForm) form;
		frm.setFullNameClient(op.getOwner().getFullName());
		frm.setDeposit(op.getEntity());
		frm.setDepositInfo(op.getDepositInfo());
	}
}
