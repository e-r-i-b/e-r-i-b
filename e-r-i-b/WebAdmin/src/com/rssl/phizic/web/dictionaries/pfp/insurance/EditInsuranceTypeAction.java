package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.insurance.InsuranceType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.insurance.EditInsuranceTypeOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceTypeAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditInsuranceTypeOperation operation = createOperation(EditInsuranceTypeOperation.class);
		EditInsuranceTypeForm form = (EditInsuranceTypeForm) frm;
		operation.initialize(form.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditInsuranceTypeForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		InsuranceType type = (InsuranceType) entity;
		type.setName((String) data.get("name"));
		type.setDescription((String) data.get("description"));
	}


	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
		EditInsuranceTypeOperation operation = (EditInsuranceTypeOperation) editOperation;
		Long parentId = (Long) validationResult.get("parentId");
		if (parentId != null)
			operation.setParentType(parentId);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		InsuranceType type = (InsuranceType) entity;
		frm.setField("name", type.getName());
		frm.setField("description", type.getDescription());
		InsuranceType parent = type.getParent();
		frm.setField("parentName", parent == null? null: type.getParent().getName());
		frm.setField("parentId", parent == null? null: type.getParent().getId());
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}
}