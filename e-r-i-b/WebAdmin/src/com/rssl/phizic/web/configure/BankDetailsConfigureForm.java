package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.bank.BankDetailsConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настроек реквизитов банка, которые не могут быть получены из справочников
 * @author Pankin
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BankDetailsConfigureForm extends EditPropertiesFormBase
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

		FieldBuilder fieldBuilder;
		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankDetailsConfig.PARTICIPANT_CODE_PROPERTY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Код типа участника расчетов в сети Банка России");
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator("\\d{0,100}", "Код типа участника расчетов в сети Банка России должен содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankDetailsConfig.OGRN_PROPERTY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("ОГРН банка");
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator("\\d{13}", "ОГРН банка должен содержать 13 цифр"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
