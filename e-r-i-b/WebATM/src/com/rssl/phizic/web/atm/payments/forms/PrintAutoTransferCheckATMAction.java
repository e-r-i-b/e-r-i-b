package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoAction;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.PrintAutoSubscriptionPaymentInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Чек для автопереводов
 *
 * @author khudyakov
 * @ created 23.03.15
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoTransferCheckATMAction extends PrintAutoSubscriptionPaymentInfoAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "ClientAutotransfersManagement");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}

}
