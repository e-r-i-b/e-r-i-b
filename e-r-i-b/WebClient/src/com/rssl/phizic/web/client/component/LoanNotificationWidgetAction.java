package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.widget.LoanNotificationWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

import java.security.AccessControlException;

/**
 * @author Gololobov
 * @ created 09.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoanNotificationWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws AccessControlException, BusinessException
	{
		return createOperation(LoanNotificationWidgetOperation.class);
	}

	@Override
	protected void updateForm(WidgetForm frm, WidgetOperation op) throws BusinessException, BusinessLogicException
	{
		super.updateForm(frm, op);

		LoanNotificationWidgetOperation operation = (LoanNotificationWidgetOperation) op;
		LoanNotificationWidgetForm form = (LoanNotificationWidgetForm) frm;

		//список всех кредитов клиента
		form.setLoans(operation.getLoanLinks());

		//кредит, выбранный в виджете
		form.setWidgetLoanLink(operation.getLoanLink());

		//За сколько до дня расплаты оповещать клиента об очередном платеже по кредиту
		form.setLoanNotifyDayCount(operation.getNotifyDays());

		//Дней до следующего срока оплаты по кредиту
		form.setBeforeNextPaymentDaysCount(operation.getDaysLeft());

		form.setAlarm(operation.isAlarm());

		form.setTitle(operation.getTitle());
	}
}
