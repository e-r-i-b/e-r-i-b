package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.autosubscription.ListAutoSubscriptionOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Печатная форма списка автопереводов
 *
 * @author khudyakov
 * @ created 02.11.14
 * @ $Author$
 * @ $Revision$
 */
public class PrintAutoTransfersAction extends ListActionBase
{
	@Override
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListAutoSubscriptionOperation operation = createOperation("ListAutoSubscriptionOperation", "AutoTransfersManagement");
		operation.initialize(InternalCardsTransferLongOffer.class, ExternalCardsTransferToOurBankLongOffer.class);
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
