package com.rssl.phizic.web.bannedAccount;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditBannedAccountForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Маска счета
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Счет");
		fieldBuilder.setName("account");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{20}", "Счет должен быть 20 символов"),
				new RegexpFieldValidator("(\\d*\\**)+","Маскировать счет можно только символом «*», например 30202**************")
		);
		fb.addField(fieldBuilder.build());

		// Поле БИК - список через запятую для записи в базу
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Список БИК");
		fieldBuilder.setName("BICList");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators(
			   new RegexpFieldValidator(".{0,500}", "Вы превысили максимально допустимое количество БИКов для маски счета")
		);
		fb.addField(fieldBuilder.build());

		//тип запрета(enum : физик, юрик, все)
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип запрета");
		fieldBuilder.setName("banType");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

}
