package com.rssl.phizic.web.client.accounts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.print.AccountInfoPrintOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * User: Novikov_A
 * Date: 25.12.2006
 * Time: 18:06:35
 */
public class PrintAccountInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAccountInfoForm frm = (PrintAccountInfoForm) form;
		AccountInfoPrintOperation operation = createOperation(AccountInfoPrintOperation.class);
		operation.initialize(frm.getAccountId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form)
			throws BusinessException
	{
		PrintAccountInfoForm frm = (PrintAccountInfoForm) form;
		AccountInfoPrintOperation op = (AccountInfoPrintOperation) operation;
		frm.setAccount(op.getEntity());
		frm.setFullNameClient(op.getOwner().getFullName());
	}
}
