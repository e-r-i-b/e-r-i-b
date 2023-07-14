package com.rssl.phizic.web.atm.moneyBox;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.autosubscription.GetAutoSubscriptionInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.payments.forms.autosubscription.ShowAutoSubscriptionInfoForm;
import com.rssl.phizic.web.atm.autosubscriptions.ShowAutoSubscriptionAbstractATMAction;
import com.rssl.phizic.web.atm.common.FilterFormBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Date;

/**
 * @author vagin
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 * Экшен получения графика исполнения платежей по копилке.
 */
public class ShowMoneyBoxAbstractATMAction extends ShowAutoSubscriptionAbstractATMAction
{
	public ViewEntityOperation createViewEntityOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowAutoSubscriptionInfoForm frm = (ShowAutoSubscriptionInfoForm) form;
		return operationInitialize(frm, createOperation(GetAutoSubscriptionInfoOperation.class, "MoneyBoxManagement"));
	}

	protected GetAutoSubscriptionInfoOperation operationInitialize(ShowAutoSubscriptionInfoForm form, GetAutoSubscriptionInfoOperation operation) throws BusinessException, BusinessLogicException
	{
		Date fromDate = (Date) form.getFilter(FilterFormBase.FROM_DATE_NAME);
		Date toDate = (Date) form.getFilter(FilterFormBase.TO_DATE_NAME);
		if (fromDate != null && toDate != null)
		{
			//сетим время полными днями
			fromDate = DateHelper.setTime(fromDate, 0, 0, 0, 0);
			toDate = DateHelper.setTime(toDate, 23, 59, 59, 0);
			operation.initialize(form.getId(), DateHelper.toCalendar(fromDate), DateHelper.toCalendar(toDate));
		}
		else
			operation.initialize(form.getId());

		return operation;
	}
}
