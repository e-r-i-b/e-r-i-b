package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoAction;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author basharin
 * @ created 27.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionPaymentInfoForEmployeeAction extends PrintAutoSubscriptionPaymentInfoAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "AutoSubscriptionManagment");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}
}
