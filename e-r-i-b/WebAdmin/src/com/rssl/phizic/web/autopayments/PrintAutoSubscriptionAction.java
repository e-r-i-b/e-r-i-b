package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * �������� ����� ������ ������������
 *
 * @author: vagin
 * @ created: 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoSubscriptionAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListAutoSubscriptionOperation operation = createOperation("ListAutoSubscriptionOperation","AutoSubscriptionManagment");
		operation.initialize(CardPaymentSystemPaymentLongOffer.class);
		return operation;
	}

	@Override
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		PrintAutoSubscriptionForm frm = (PrintAutoSubscriptionForm) form;
		ListAutoSubscriptionOperation op = (ListAutoSubscriptionOperation) operation;

		frm.setData(op.getAutoSubscriptionLinks());
	}
}

