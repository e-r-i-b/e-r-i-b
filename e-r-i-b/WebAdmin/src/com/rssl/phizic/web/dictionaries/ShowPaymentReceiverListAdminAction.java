package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.dictionaries.RemovePaymentReceiverOperation;
import com.rssl.phizic.operations.dictionaries.receivers.GetPaymentReceiverListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.web.common.dictionaries.ShowPaymentReceiverListActionBase;
import com.rssl.phizic.web.common.dictionaries.ShowPaymentReceiverListForm;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;

/**
 * @author Krenev
 * @ created 12.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowPaymentReceiverListAdminAction extends ShowPaymentReceiverListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowPaymentReceiverListForm frm = (ShowPaymentReceiverListForm) form;

		GetPaymentReceiverListOperation operation = createOperation(GetPaymentReceiverListOperation.class, getCurrentMapping().getParameter());
		operation.initialize(frm.getPerson());

		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		ShowPaymentReceiverListForm frm = (ShowPaymentReceiverListForm) form;
		RemovePaymentReceiverOperation operation = createOperation(RemovePaymentReceiverOperation.class, getCurrentMapping().getParameter());
		operation.setPersonId(frm.getPerson());
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowPaymentReceiverListForm.FILTER_FORM;
	}
}
