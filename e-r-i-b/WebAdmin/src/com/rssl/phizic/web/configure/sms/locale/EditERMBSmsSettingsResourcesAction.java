package com.rssl.phizic.web.configure.sms.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.sms.locale.SmsResourceResources;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.operations.sms.locale.EditERMBSmsSettingsResourcesOperation;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditERMBSmsSettingsResourcesAction extends EditCSASmsSettingsResourcesAction
{
	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
		SmsResourceResources resource = (SmsResourceResources)entity;
		resource.setText((String) data.get(EditERMBSmsSettingsResourcesForm.FIELD_ERMB_TEXT));
	}

	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
		frm.setField(EditERMBSmsSettingsResourcesForm.FIELD_ERMB_TEXT, ((SmsResourceResources) entity).getText());
	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditERMBSmsSettingsResourcesForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditERMBSmsSettingsResourcesOperation.class);
	}
}
