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

		//������ ���� �������� �������
		form.setLoans(operation.getLoanLinks());

		//������, ��������� � �������
		form.setWidgetLoanLink(operation.getLoanLink());

		//�� ������� �� ��� �������� ��������� ������� �� ��������� ������� �� �������
		form.setLoanNotifyDayCount(operation.getNotifyDays());

		//���� �� ���������� ����� ������ �� �������
		form.setBeforeNextPaymentDaysCount(operation.getDaysLeft());

		form.setAlarm(operation.isAlarm());

		form.setTitle(operation.getTitle());
	}
}
