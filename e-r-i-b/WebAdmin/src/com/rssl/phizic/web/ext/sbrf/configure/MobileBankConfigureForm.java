package com.rssl.phizic.web.ext.sbrf.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.jmx.MobileBankConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * User: Moshenko
 * Date: 06.09.2012
 * Time: 17:09:36
 */
public class MobileBankConfigureForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	@Override
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Перерыв запуска процедуры подключения услуги");
		fieldBuilder.setName(MobileBankConfig.REGISTRATION_REPEAT_INTERVAL);
		fieldBuilder.setType("integer");
		RegexpFieldValidator regexpFieldValidator = new RegexpFieldValidator("\\d{1,9}");
		fieldBuilder.addValidators( new RequiredFieldValidator("Укажите время перерыва запуска процедуры подключения услуги."),regexpFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
