package com.rssl.phizic.web.loans.statemessages;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.operations.loans.statemessages.EditLoanStateMessagesOperations;
import com.rssl.phizic.operations.EditEntityOperation;
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
public class EditLoanStateMessagesAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase form) throws BusinessException, BusinessLogicException
	{
		EditLoanStateMessagesForm frm = (EditLoanStateMessagesForm) form;
		EditLoanStateMessagesOperations operation = createOperation(EditLoanStateMessagesOperations.class,"LoanStateMessages");
		operation.initialize(frm.getKey());

		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditLoanStateMessagesForm.EDIT_FORM;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		frm.setField("value", entity);
	}

	protected void updateEntity(Object entity, Map<String, Object> data) {}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase form, Map<String, Object> validationResult)
	{
		EditLoanStateMessagesForm frm = (EditLoanStateMessagesForm) form;
		EditLoanStateMessagesOperations op = (EditLoanStateMessagesOperations) editOperation;

		op.setKey(frm.getKey());
		op.setValue((String) frm.getField("value"));
	}
}


