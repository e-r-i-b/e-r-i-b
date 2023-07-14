package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ClientConfig;

/**
 * Форма редактирования настроек для меню
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsMenuForm extends EditPropertiesFormBase
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ClientConfig.NEWS_COUNT);
		fieldBuilder.setType("integer");
		fieldBuilder.setDescription("Количество новостей в правом меню");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,20}", "Поле \"Количество новостей в правом меню\" должно содержать не больше 20 цифр."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return form;
	}
}
