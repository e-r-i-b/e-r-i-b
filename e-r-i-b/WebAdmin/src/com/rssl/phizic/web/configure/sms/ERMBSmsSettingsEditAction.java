package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.sms.ERMBSmsSettingsEditOperation;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Map;

/**
 * Экшн редактирования текста смс-шаблона ЕРМБ
 * @author Rtischeva
 * @created 31.07.13
 * @ $Author$
 * @ $Revision$
 */
public class ERMBSmsSettingsEditAction extends SmsSettingsEditAction
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ERMBSmsSettingsEditOperation operation = createOperation("ERMBSmsSettingsEditOperation", "SMSManagement");
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return ERMBSmsSettingsEditForm.FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		SMSResource resource = (SMSResource) entity;
		String newText = (String) data.get(ERMBSmsSettingsEditForm.FIELD_ERMB_TEXT);
		Long newPriority=(Long) data.get(ERMBSmsSettingsEditForm.FIELD_PRIORITY);
		applyChangesInfo(resource, textChanged(newText, resource.getText()));

		resource.setText(newText);
		resource.setPriority(newPriority);
		resource.setDescription((String) data.get(ERMBSmsSettingsEditForm.FIELD_DESCRIPTION));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Map<String, Object> fields    = frm.getFields();
		SMSResource resource = (SMSResource) entity;

		fields.put(ERMBSmsSettingsEditForm.FIELD_PRIORITY,    resource.getPriority());
		fields.put(ERMBSmsSettingsEditForm.FIELD_ID,          resource.getId());
		fields.put(ERMBSmsSettingsEditForm.FIELD_ERMB_TEXT,     resource.getText());
		fields.put(ERMBSmsSettingsEditForm.FIELD_DESCRIPTION, resource.getDescription());
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult)
	{
	}
}
