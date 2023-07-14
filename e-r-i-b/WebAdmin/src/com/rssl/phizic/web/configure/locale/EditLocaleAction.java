package com.rssl.phizic.web.configure.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.LocaleState;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.locale.EditLocaleOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import com.rssl.phizic.web.image.ImageEditFormBase;
import org.apache.struts.action.ActionMessages;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLocaleAction extends ImageEditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditLocaleForm form = (EditLocaleForm)frm;
		EditLocaleOperation operation = createOperation(form);
		String id = form.getLocaleId();
		if (StringHelper.isNotEmpty(id))
		{
			operation.initialize(id);
		}
		else
		{
			operation.initialize();
		}
		return operation;
	}

	protected EditLocaleOperation createOperation(EditLocaleForm form) throws BusinessException
	{
		form.setIsCSA(false);
		return createOperation(EditLocaleOperation.class);
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return EditLocaleForm.EDIT_FORM;
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		ERIBLocale locale = (ERIBLocale) entity;
		locale.setName((String) data.get("name"));
		if (StringHelper.isEmpty(locale.getId()))
			locale.setId((String) data.get("id"));
		locale.setState(LocaleState.valueOf((String) data.get("state")));
		locale.setEribAvailable((Boolean) data.get("eribAvailable"));
		locale.setMapiAvailable((Boolean) data.get("mapiAvailable"));
		locale.setAtmApiAvailable((Boolean) data.get("atmApiAvailable"));
		locale.setWebApiAvailable((Boolean) data.get("webApiAvailable"));
		locale.setErmbAvailable((Boolean) data.get("ermbAvailable"));
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		ERIBLocale locale = (ERIBLocale) entity;
		frm.setField("name", locale.getName());
		frm.setField("id", locale.getId());
		frm.setField("state", locale.getState());
		frm.setField("eribAvailable", locale.isEribAvailable());
		frm.setField("mapiAvailable", locale.isMapiAvailable());
		frm.setField("atmApiAvailable", locale.isAtmApiAvailable());
		frm.setField("webApiAvailable", locale.isWebApiAvailable());
		frm.setField("ermbAvailable", locale.isErmbAvailable());

	}
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		updateOperationImageData(editOperation, editForm, validationResult);
	}

	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	protected ActionMessages validate(ImageEditFormBase form, String imageId)
	{
		if(form.isEmptyImage(imageId))
			return getImageActionMessages("Не задано изображение для локали", imageId);
		return null;
	}

	protected ActionMessages validateDiscSource(ImageEditFormBase form, String imageId)
	{
		ActionMessages actionMessages = validate(form, imageId);
		if(actionMessages != null)
			return actionMessages;
		return super.validateDiscSource(form, imageId);
	}
}
