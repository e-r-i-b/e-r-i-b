package com.rssl.phizic.web.common.mobile.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.mobile.autosubscriptions.ShowAutoSubscriptionAbstractMobileAction;

/**
 * @author vagin
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * Экшен получения графика исполнения платежей по копилке.
 */
public class ShowMoneyBoxAbstractMobileAction extends ShowAutoSubscriptionAbstractMobileAction
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		return operationInitialize(frm, createOperation(GetAutoSubscriptionInfoOperation.class, "MoneyBoxManagement"));
	}
}
