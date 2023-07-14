package com.rssl.phizic.web.client.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.dictionaries.RemovePaymentReceiverOperationClient;
import com.rssl.phizic.operations.dictionaries.receivers.GetPaymentReceiverListOperationClient;
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
public class ShowPaymentReceiverListClientAction extends ShowPaymentReceiverListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetPaymentReceiverListOperationClient operation = createOperation(GetPaymentReceiverListOperationClient.class, getCurrentMapping().getParameter());
		operation.initialize();
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		RemovePaymentReceiverOperationClient operation = createOperation(RemovePaymentReceiverOperationClient.class, getCurrentMapping().getParameter());
		operation.setPersonId(personData.getPerson().getId());
		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowPaymentReceiverListForm.FILTER_FORM;
	}
}

