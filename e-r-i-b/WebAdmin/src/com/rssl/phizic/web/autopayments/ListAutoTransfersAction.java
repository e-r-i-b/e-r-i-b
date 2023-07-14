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
 * Список автопереводов
 *
 * @author khudyakov
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoTransfersAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListAutoSubscriptionOperation listAutoSubscriptionOperation = createOperation("ListAutoSubscriptionOperation", "AutoTransfersManagement");
		listAutoSubscriptionOperation.initialize(InternalCardsTransferLongOffer.class, ExternalCardsTransferToOurBankLongOffer.class);

		return listAutoSubscriptionOperation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ListAutoSubscriptionForm frm = (ListAutoSubscriptionForm) form;
		ListAutoSubscriptionOperation listAutoSubscriptionOperation = (ListAutoSubscriptionOperation) operation;
		frm.setActivePerson(listAutoSubscriptionOperation.getPerson());
		frm.setData(listAutoSubscriptionOperation.getAutoSubscriptionLinks());
	}
}
