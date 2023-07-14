package com.rssl.phizic.web.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.simple.pension.fund.PensionFund;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.simple.pension.fund.EditPensionFundOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€ пенсионного фонда
 */

public class EditPensionFundAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditPensionFundOperation operation = createOperation(EditPensionFundOperation.class);
		Long id = frm.getId();
		if (id == null)
			operation.initialize();
		else
			operation.initialize(id);
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditPensionFundForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		PensionFund pensionFund = (PensionFund) entity;
        pensionFund.setName((String) data.get(EditPensionFundForm.NAME_FIELD_NAME));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		PensionFund pensionFund = (PensionFund) entity;
	   	frm.setField(EditPensionFundForm.NAME_FIELD_NAME, pensionFund.getName());
	}

	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation operation, EditFormBase form, Map<String, Object> data) throws Exception
	{
		updateOperationImageData(operation, form, data);
	}
}
