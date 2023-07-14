package com.rssl.phizic.web.configure.sms;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messages.MessageResource;
import com.rssl.phizic.business.sms.SMSResource;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.sms.SmsSettingsEditOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author  Balovtsev
 * @version 22.03.13 11:32
 */
public class SmsSettingsEditAction extends EditActionBase
{
	@Override
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		SmsSettingsEditOperation operation = createOperation("SmsSettingsEditOperation", "SMSManagement");
		operation.initialize(frm.getId());
		return operation;
	}

	@Override
	protected Form getEditForm(EditFormBase frm)
	{
		return SmsSettingsEditForm.FORM;
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation editOperation, EditFormBase editForm, Map<String, Object> validationResult) throws Exception
	{
		for (Object item : (List) editOperation.getEntity())
		{
			SMSResource resource = (SMSResource) item;

			String newText  = null;
			String prevText = resource.getText();

			switch (resource.getChannel())
			{
				case MOBILE_API:
				{
					boolean custom = (Boolean) validationResult.get(SmsSettingsEditForm.FIELD_MAPI_TEMPLATE_TYPE);

					if (custom)
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_MAPITEXT);
					}
					else
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_ERIBTEXT);
					}

					resource.setCustom(custom);
					resource.setPriority(Long.valueOf((String)editForm.getFields().get(SmsSettingsEditForm.FIELD_PRIORITY)));
					break;
				}
                case SOCIAL_API:
				{
					boolean custom = (Boolean) validationResult.get(SmsSettingsEditForm.FIELD_SOCIAL_TEMPLATE_TYPE);

					if (custom)
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_SOCIALTEXT);
					}
					else
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_ERIBTEXT);
					}

					resource.setCustom(custom);
					resource.setPriority(Long.valueOf((String)editForm.getFields().get(SmsSettingsEditForm.FIELD_PRIORITY)));
					break;
				}
				case SELF_SERVICE_DEVICE:
				{
					boolean custom = (Boolean) validationResult.get(SmsSettingsEditForm.FIELD_ATM_TEMPLATE_TYPE);

					if (custom)
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_ATMTEXT);
					}
					else
					{
						newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_ERIBTEXT);
					}

					resource.setCustom(custom);
					resource.setPriority(Long.valueOf((String)editForm.getFields().get(SmsSettingsEditForm.FIELD_PRIORITY)));
					break;
				}
				default:
				{
					newText = (String) validationResult.get(SmsSettingsEditForm.FIELD_ERIBTEXT);
					resource.setDescription((String) validationResult.get(SmsSettingsEditForm.FIELD_DESCRIPTION));
					resource.setPriority(Long.valueOf((String)editForm.getFields().get(SmsSettingsEditForm.FIELD_PRIORITY)));
				}
			}

			applyChangesInfo(resource, textChanged(newText, prevText));
			resource.setText(newText);
		}
	}

	@Override
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		List items = (List) entity;
		Map<String, Object> fields = frm.getFields();

		for (Object item : items)
		{
			SMSResource resources   = (SMSResource) item;
			String      description = resources.getDescription();

			if (StringHelper.isNotEmpty(description))
			{
				fields.put(SmsSettingsEditForm.FIELD_DESCRIPTION, description);
			}

			switch (resources.getChannel())
			{
				case MOBILE_API:
				{
					fields.put(SmsSettingsEditForm.FIELD_MAPITEXT, resources.getText());
					fields.put(SmsSettingsEditForm.FIELD_MAPI_TEMPLATE_TYPE, resources.getCustom());
					break;
				}
				case SELF_SERVICE_DEVICE:
				{
					fields.put(SmsSettingsEditForm.FIELD_ATMTEXT, resources.getText());
					fields.put(SmsSettingsEditForm.FIELD_ATM_TEMPLATE_TYPE, resources.getCustom());
					break;
				}
                case SOCIAL_API:
                {
                    fields.put(SmsSettingsEditForm.FIELD_SOCIALTEXT, resources.getText());
                    fields.put(SmsSettingsEditForm.FIELD_SOCIAL_TEMPLATE_TYPE, resources.getCustom());
					break;
				}
				default:
				{
					fields.put(SmsSettingsEditForm.FIELD_ID,       resources.getId());
					fields.put(SmsSettingsEditForm.FIELD_ERIBTEXT, resources.getText());
					fields.put(SmsSettingsEditForm.FIELD_PRIORITY, resources.getPriority());
				}
			}

			fields.put("key", resources.getKey());
		}
	}

	@Override
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception {}

	protected void applyChangesInfo(MessageResource resource, boolean hasChanges)
	{
		if (!hasChanges)
		{
			return;
		}

		resource.setPreviousText(resource.getText());
		resource.setLastModified(Calendar.getInstance());
		resource.setEmployeeLoginId(AuthenticationContext.getContext().getLogin().getId());
	}

	protected boolean textChanged(String newText, String prevText)
	{
		newText  = newText.replaceAll ("\\r", "");
		prevText = prevText.replaceAll("\\r", "");

		return !prevText.equals(newText);
	}
}
