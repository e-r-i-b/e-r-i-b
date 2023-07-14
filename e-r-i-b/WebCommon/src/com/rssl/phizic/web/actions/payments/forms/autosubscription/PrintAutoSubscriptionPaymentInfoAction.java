package com.rssl.phizic.web.actions.payments.forms.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionPaymentInfoOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author basharin
 * @ created 27.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class PrintAutoSubscriptionPaymentInfoAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation operation = createOperation(GetAutoSubscriptionPaymentInfoOperation.class, "AutoSubscriptionLinkManagment");
		operation.initialize(form.getSubscriptionId(), form.getId());
		return operation;
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		PrintAutoSubscriptionPaymentInfoForm form = (PrintAutoSubscriptionPaymentInfoForm) frm;
		GetAutoSubscriptionPaymentInfoOperation op = (GetAutoSubscriptionPaymentInfoOperation) operation;

		form.setPayment(op.getEntity());
		form.setActivePerson(op.getPerson());
		form.setDatePayment(DateHelper.toDate(op.getEntity().getExecutionDate()));
	}
}
