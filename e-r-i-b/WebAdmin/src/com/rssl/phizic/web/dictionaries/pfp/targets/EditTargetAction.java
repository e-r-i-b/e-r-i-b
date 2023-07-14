package com.rssl.phizic.web.dictionaries.pfp.targets;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.targets.EditTargetOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditTargetOperation operation = createOperation(EditTargetOperation.class);
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditTargetForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data)
	{
		Target target = (Target) entity;
		target.setName((String) data.get("name"));
		target.setOnlyOne((Boolean) data.get("onlyOne"));
		target.setLaterAll((Boolean) data.get("laterAll"));
		target.setLaterLoans((Boolean) data.get("laterLoans"));
	}

	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}

	protected void updateForm(EditFormBase frm, Object entity)
	{
		Target target = (Target) entity;
		EditTargetForm form = (EditTargetForm) frm;
		form.setField("name", target.getName());
		form.setField("onlyOne", target.isOnlyOne());
		form.setField("laterAll", target.isLaterAll());
		form.setField("laterLoans", target.isLaterLoans());
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}
}
