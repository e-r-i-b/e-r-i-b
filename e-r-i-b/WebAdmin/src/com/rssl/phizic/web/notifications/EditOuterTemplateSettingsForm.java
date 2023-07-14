package com.rssl.phizic.web.notifications;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.notification.BusinessNotificationConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * форма настроек редактирования внешнего шаблона
 * @author tisov
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class EditOuterTemplateSettingsForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(BusinessNotificationConfig.PATH_KEY);
		fb.setDescription("Путь к внешнему шаблону");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
