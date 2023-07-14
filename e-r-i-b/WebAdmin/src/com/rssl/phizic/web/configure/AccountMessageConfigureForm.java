package com.rssl.phizic.web.configure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.account.AccountMessageConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настроек сообщения объясняющего максимальную сумму вклада
 * @author Pankin
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AccountMessageConfigureForm extends EditPropertiesFormBase
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
		fieldBuilder.setName(AccountMessageConfig.ACCOUNT_MAX_BALANCE_MESSAGE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Подсказка детальной информации");
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator(".{1,300}", "Наименование должно быть не более 300 символов"));

		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(AccountMessageConfig.ACCOUNT_MAX_BALANCE_VALIDATOR_MESSAGE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Сообщение валидатора");
		fieldBuilder.addValidators(requiredFieldValidator,
				new RegexpFieldValidator(".{1,300}", "Наименование должно быть не более 300 символов"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
