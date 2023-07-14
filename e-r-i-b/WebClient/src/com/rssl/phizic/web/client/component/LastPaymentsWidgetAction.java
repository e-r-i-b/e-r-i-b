package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.ListPopularPaymentsOperation;
import com.rssl.phizic.operations.widget.LastPaymentsWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

/** Экшн виджета "Последние платежи"
 * @ author Rtischeva
 * @ created 17.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LastPaymentsWidgetAction extends WidgetActionBase
{
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(LastPaymentsWidgetOperation.class);
	}

	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);
		LastPaymentsWidgetOperation widgetOperation = (LastPaymentsWidgetOperation) operation;
		LastPaymentsWidgetForm frm = (LastPaymentsWidgetForm) form;
		ListPopularPaymentsOperation oper = createOperation("ListPopularPaymentsOperation", "RurPayJurSB");
		widgetOperation.setListPopularPaymentsOperation(oper);
		frm.setPayments(widgetOperation.getUserPayments());
	}
}
