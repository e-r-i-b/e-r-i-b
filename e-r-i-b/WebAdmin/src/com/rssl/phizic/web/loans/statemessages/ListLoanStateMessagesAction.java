package com.rssl.phizic.web.loans.statemessages;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.loans.statemessages.ListLoanStateMessagesOperations;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 21.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListLoanStateMessagesAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListLoanStateMessagesOperations operation = createOperation(ListLoanStateMessagesOperations.class,"LoanStateMessages");
		operation.initialize();

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return EditLoanStateMessagesForm.EDIT_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form)
	{
		ListLoanStateMessagesForm frm = (ListLoanStateMessagesForm) form;
		ListLoanStateMessagesOperations op = (ListLoanStateMessagesOperations) operation;

		frm.setProperties(op.getProperties());
	}
}
