package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoAction;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Чек по операции автоперевода (админка)
 *
 * @author khudyakov
 * @ created 03.11.14
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoTransferPaymentInfoEmployeeAction extends PrintAutoSubscriptionPaymentInfoAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "AutoTransfersManagement");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}
}
