package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author komarov
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class MailSettingsForm extends EditPropertiesFormBase
{
	private static final Form MAIL_SETTINGS_FORM = createForm();

	@Override
	public Form getForm()
	{
		return MAIL_SETTINGS_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MailConfig.PROPERTY_CLIENT_TEXT_LENGTH);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Количество символов, доступное для ввода клиентам в поле Сообщение");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,4}")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MailConfig.PROPERTY_EMPLOYEE_TEXT_LENGTH);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Количество символов, доступных для ввода в поле Текст письма");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,4}")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
