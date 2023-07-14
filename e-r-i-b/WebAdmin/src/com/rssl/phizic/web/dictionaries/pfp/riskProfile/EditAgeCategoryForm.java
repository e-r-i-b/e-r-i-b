package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditAgeCategoryForm extends EditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minAge");
		fieldBuilder.setDescription("Минимальный возраст");
		fieldBuilder.clearValidators();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, укажите минимальный возраст клиентов, относящихся к данной возрастной категории."),
								   new RegexpFieldValidator("(\\d{0,3})", "Поле Возраст должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAge");
		fieldBuilder.setDescription("Максимальный возраст");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("(\\d{0,3})", "Поле Возраст должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("weight");
		fieldBuilder.setDescription("Баллы");
		fieldBuilder.clearValidators();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator("(\\d{0,2})", "Поле \"Баллы\" должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minAge");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxAge");
		compareValidator.setMessage("Вы неправильно указали минимальный возраст клиентов. Минимальный возраст должен быть меньше максимального возраста.");

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}
}
