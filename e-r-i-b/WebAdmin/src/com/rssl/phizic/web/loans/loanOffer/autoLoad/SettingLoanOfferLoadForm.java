package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author Mescheryakova
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanOfferLoadForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	protected static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Наименование
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Каталог");
		fieldBuilder.setName("path");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,255}", "Длина названия каталога должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		// Наименование
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название файла");
		fieldBuilder.setName("file");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,255}", "Длина названия файла должна быть не более 255 символов")

		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}