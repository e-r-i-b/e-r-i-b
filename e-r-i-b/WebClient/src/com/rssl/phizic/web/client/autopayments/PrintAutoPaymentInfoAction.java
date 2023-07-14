package com.rssl.phizic.web.client.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autopayments.GetAutoPaymentInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author osminin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoPaymentInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoPaymentInfoForm frm = (PrintAutoPaymentInfoForm) form;
		GetAutoPaymentInfoOperation operation = createOperation(GetAutoPaymentInfoOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		PrintAutoPaymentInfoForm frm = (PrintAutoPaymentInfoForm) form;
		GetAutoPaymentInfoOperation op = (GetAutoPaymentInfoOperation) operation;
		frm.setLink(op.getEntity());
		frm.setCurrentDepartment(op.getCurrentDepartment());
		frm.setTopLevelDepartment(op.getTopLevelDepartment());
		op.getTopLevelDepartment();
	}
}
