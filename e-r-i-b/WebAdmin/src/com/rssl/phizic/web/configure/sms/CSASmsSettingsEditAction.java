package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.sms.CSASmsResource;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.sms.CSASmsSettingsEditOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 *
 * Action для редактирования смс ресурсов из ЦСА
 *
 * @author  Balovtsev
 * @version 05.04.13 12:06
 */
public class CSASmsSettingsEditAction extends SmsSettingsEditAction
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		CSASmsSettingsEditOperation operation = createOperation("CSASmsSettingsEditOperation", "SMSManagement");
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return CSASmsSettingsEditForm.FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		CSASmsResource resources = (CSASmsResource) entity;

		String newText = (String) data.get(CSASmsSettingsEditForm.FIELD_CSATEXT);
		applyChangesInfo(resources, textChanged(newText, resources.getText()));

		resources.setText(newText);
		resources.setDescription((String) data.get(CSASmsSettingsEditForm.FIELD_DESCRIPTION));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Map<String, Object> fields    = frm.getFields();
		CSASmsResource resources = (CSASmsResource) entity;

		fields.put(CSASmsSettingsEditForm.FIELD_ID,          resources.getId());
		fields.put(CSASmsSettingsEditForm.FIELD_CSATEXT,     resources.getText());
		fields.put(CSASmsSettingsEditForm.FIELD_DESCRIPTION, resources.getDescription());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
	}
}
