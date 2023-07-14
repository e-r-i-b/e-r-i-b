package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Настройки atmAPI
 * @author Dorzhinov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class AtmApiConfigureForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		//showServices
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AtmApiConfig.SHOW_SERVICES);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription("Отображение групп услуг в каталоге поставщиков");
		fieldBuilder.addValidators(requiredFieldValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
