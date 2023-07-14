package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author vagin
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 * Ёкшен печати чека по исполненому платежу по копилке.
 */
public class PrintMoneyBoxPaymentInfoAction extends PrintAutoSubscriptionPaymentInfoAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "MoneyBoxManagement");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}
}
