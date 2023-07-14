package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Класс детальной информации по автопереводу (админка)
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public class ShowAutoTransferPaymentInfoEmployeeAction extends ShowAutoSubscriptionPaymentInfoEmployeeActionBase
{
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionPaymentInfoForEmployeeForm frm = (ShowAutoSubscriptionPaymentInfoForEmployeeForm) form;

		GetAutoSubscriptionPaymentInfoOperation operation = createOperation("GetAutoSubscriptionPaymentInfoOperation", "AutoTransfersManagement");
		operation.initialize(frm.getSubscriptionId(), frm.getId());

		return operation;
	}

	@Override
	protected GetAutoSubscriptionPaymentInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException
	{
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation("GetAutoSubscriptionPaymentInfoOperation", "AutoTransfersManagement");
		operation.initialize();

		return operation;
	}
}
