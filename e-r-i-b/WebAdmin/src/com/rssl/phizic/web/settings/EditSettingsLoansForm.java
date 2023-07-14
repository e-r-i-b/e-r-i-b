package com.rssl.phizic.web.settings;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.util.BankInfoUtil;

/**
 * Форма редактирования настроек для кредитов
 * @author basharin
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSettingsLoansForm extends EditPropertiesFormBase
{
	private static final Form form = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankInfoUtil.BANK_LOAN_LINK_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setValidators();
		fieldBuilder.setDescription("Ссылка на страницу кредитных продуктов на сайте банка");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Поле \"Ссылка на страницу кредитных продуктов на сайте банка\" не должно превышать 100 символов"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(BankInfoUtil.BANK_CARD_LINK_KEY);
		fieldBuilder.setType("string");
		fieldBuilder.setValidators();
		fieldBuilder.setDescription("Ссылка на страницу с предложениями по кредитным картам на сайте банка");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Поле \"Ссылка на страницу с предложениями по кредитным картам на сайте банка\" не должно превышать 100 символов"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return form;
	}
}