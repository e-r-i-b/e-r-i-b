package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Детальная информация по автопереводу
 *
 *
 * @author khudyakov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoTransferInfoEmployeeAction extends ShowAutoSubscriptionInfoEmployeeActionBase
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		return operationInitialize((ShowAutoSubscriptionInfoForm) form, createOperation(GetAutoSubscriptionInfoOperation.class, "AutoTransfersManagement"));
	}

	public void updateForm(GetAutoSubscriptionInfoOperation operation, ShowAutoSubscriptionInfoForm form) throws BusinessException, BusinessLogicException
	{
		super.updateForm(operation, form);

		ShowAutoSubscriptionInfoForEmployeeForm frm = (ShowAutoSubscriptionInfoForEmployeeForm) form;
		frm.setActivePerson(operation.getPerson());
	}

	protected GetAutoSubscriptionInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException
	{
		GetAutoSubscriptionInfoOperation operation = createOperation(GetAutoSubscriptionInfoOperation.class,"AutoTransfersManagement");
		operation.initialize();

		return operation;
	}
}