package com.rssl.phizic.web.autopayments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author bogdanov
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowAutoSubscriptionInfoForEmployeeAction extends ShowAutoSubscriptionInfoEmployeeActionBase
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		return operationInitialize((ShowAutoSubscriptionInfoForm) form, createOperation(GetAutoSubscriptionInfoOperation.class, "AutoSubscriptionManagment"));
	}

	protected GetAutoSubscriptionInfoOperation createSimpleViewEntityOperation() throws BusinessException, BusinessLogicException
	{
		GetAutoSubscriptionInfoOperation operation = createOperation(GetAutoSubscriptionInfoOperation.class,"AutoSubscriptionManagment");
		operation.initialize();

		return operation;
	}
}
