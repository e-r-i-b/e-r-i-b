package com.rssl.phizic.web.configure.sms.locale;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.sms.locale.SmsResourceResources;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;
import com.rssl.phizic.operations.sms.locale.EditSmsSettingsResourcesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.configure.sms.SmsSettingsEditForm;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseAction;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.util.Map;

/**
 * @author koptyaev
 * @ created 08.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditSmsSettingsResourcesAction extends EditLanguageResourcesBaseAction
{
	@Override
	protected void updateForm(EditLanguageResourcesBaseForm frm, LanguageResources entity) throws Exception
	{
	}

	@Override
	protected void updateFormOperationalData(EditLanguageResourcesBaseForm frm, EditLanguageResourcesOperation operation) throws Exception
	{
		EditSmsSettingsResourcesOperation op = (EditSmsSettingsResourcesOperation) operation;
		Map<ChannelType,SmsResourceResources> resources = op.getEntities();
		for (Map.Entry<ChannelType,SmsResourceResources> resource : resources.entrySet())
		{
			SmsResourceResources res = resource.getValue();
			switch (resource.getKey())
			{
				case MOBILE_API:
				{
					frm.setField(EditSmsSettingsResourcesForm.FIELD_MAPITEXT, res.getText());
					break;
				}
				case SOCIAL_API:
				{
					frm.setField(EditSmsSettingsResourcesForm.FIELD_SOCIALTEXT, res.getText());
					break;
				}
				case SELF_SERVICE_DEVICE:
				{
					frm.setField(EditSmsSettingsResourcesForm.FIELD_ATMTEXT, res.getText());
					break;
				}
				default:
				{
					frm.setField(EditSmsSettingsResourcesForm.FIELD_ERIBTEXT, res.getText());
					break;
				}
			}
		}
	}

	@Override
	protected void updateEntity(LanguageResources entity, Map<String, Object> data) throws Exception
	{
	}

	@Override
	protected void updateEntityOperationalData(EditEntityOperation operation, Map<String, Object> data) throws Exception
	{
		EditSmsSettingsResourcesOperation op = (EditSmsSettingsResourcesOperation) operation;
		Map<ChannelType,SmsResourceResources> resources = op.getEntities();
		for (Map.Entry<ChannelType,SmsResourceResources> resource : resources.entrySet())
		{
			SmsResourceResources res = resource.getValue();
			switch (resource.getKey())
			{
				case MOBILE_API:
				{
					if (StringHelper.isNotEmpty((String) data.get(EditSmsSettingsResourcesForm.FIELD_MAPITEXT)))
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_MAPITEXT));
					else
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_ERIBTEXT));
					break;
				}
				case SOCIAL_API:
				{
					if (StringHelper.isNotEmpty((String) data.get(EditSmsSettingsResourcesForm.FIELD_SOCIALTEXT)))
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_SOCIALTEXT));
					else
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_ERIBTEXT));
					break;
				}
				case SELF_SERVICE_DEVICE:
				{
					if (StringHelper.isNotEmpty((String) data.get(EditSmsSettingsResourcesForm.FIELD_ATMTEXT)))
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_ATMTEXT));
					else
						res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_ERIBTEXT));
					break;
				}
				default:
				{
					res.setText((String) data.get(EditSmsSettingsResourcesForm.FIELD_ERIBTEXT));
					break;
				}
			}
		}

	}

	@Override
	protected Form getEditForm(EditLanguageResourcesBaseForm frm)
	{
		return EditSmsSettingsResourcesForm.EDIT_LANGUAGE_FORM;
	}

	@Override
	protected EditLanguageResourcesOperation createEditOperation(EditLanguageResourcesBaseForm frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(EditSmsSettingsResourcesOperation.class);
	}
}
