package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.DepositConfig;

/**
 * Форма редактирования настроек для депозитов
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsDepositsForm extends EditPropertiesFormBase
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DepositConfig.ACCOUNTS_KINDS_FORBIDDEN_CLOSING);
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("Виды вкладов, недоступные для закрытия");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,100}", "Поле \"Виды вкладов, недоступные для закрытия\" должно содержать не больше 100 цифр разделенных запятыми."),
				new RegexpFieldValidator("^\\d{0,100}?(\\,\\d{1,100}?)*$", "Не правильный формат данных в поле \"Виды вкладов, недоступные для закрытия\"")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return form;
	}
}