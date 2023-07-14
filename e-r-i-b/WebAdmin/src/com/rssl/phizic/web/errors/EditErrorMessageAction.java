package com.rssl.phizic.web.errors;

import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.errors.ErrorMessage;
import com.rssl.phizic.errors.ErrorSystem;
import com.rssl.phizic.errors.ErrorType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.errors.EditErrorMessageOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author gladishev
 * @ created 19.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditErrorMessageAction extends EditActionBase
{
	protected Form getEditForm(EditFormBase frm)
	{
		return EditErrorMessageForm.FORM;
	}

	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditErrorMessageOperation operation = createOperation(EditErrorMessageOperation.class);
		Long id = frm.getId();
		if (id != null && id != 0)
		{
			operation.initialize(id);
		}
		else
		{
			operation.initializeNew();
		}
		return operation;
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		ErrorMessage errorMessage = (ErrorMessage) entity;

		frm.setField("regExp", errorMessage.getRegExp());
		frm.setField("errorType", errorMessage.getErrorType());
		frm.setField("system", errorMessage.getSystem());
		frm.setField("message", errorMessage.getMessage());
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		ErrorMessage errorMessage = (ErrorMessage) entity;

		errorMessage.setRegExp((String) data.get("regExp"));
		errorMessage.setErrorType(ErrorType.fromValue((String) data.get("errorType")));
		errorMessage.setSystem(ErrorSystem.fromValue((String) data.get("system")));
		errorMessage.setMessage((String) data.get("message"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult)
	{
		EditErrorMessageOperation op = (EditErrorMessageOperation) editOperation;

		if(op.isNew())
			currentRequest().setAttribute("$$newId", op.getEntity().getId());
	}
}
