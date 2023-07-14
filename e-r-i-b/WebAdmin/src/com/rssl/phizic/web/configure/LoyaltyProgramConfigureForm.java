package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import static com.rssl.phizic.config.loyalty.LoyaltyConfig.*;

/**
 * @author lukina
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoyaltyProgramConfigureForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(URL_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("URL-адрес сайта программы лояльности");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "URL-адрес сайта программы лояльности не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CERT_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Сертификат программы лояльности с открытым ключом");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Сертификат программы лояльности с открытым ключом не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PRIV_CERT_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Сертификат программы лояльности с закрытым ключом");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Сертификат программы лояльности с закрытым ключом не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_TYPE);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Тип хранилища сертификатов программы лояльности");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Тип хранилища сертификатов программы лояльности не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_PATH);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Путь к хранилищу сертификатов программы лояльности");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Путь к хранилищу сертификатов программы лояльности не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(STORE_PASSWORD);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Пароль для доступа к хранилищу сертификатов программы лояльности");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,100}", "Пароль для доступа к хранилищу сертификатов программы лояльности не должен превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
