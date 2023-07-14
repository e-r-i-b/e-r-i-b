package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Balovtsev
 * @since 15.10.14.
 */
public class MapiEditAccountEntityForm extends EditFormBase
{
	public static final String NAME_FIELD         = "name";
	public static final String TYPE_FIELD         = "type";

	public static final Form EDIT_FORM     = createEditForm();

	private String type;

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NAME_FIELD);
		fieldBuilder.setDescription("Наименование объекта учета");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Наименование объекта учета не должно превышать 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(TYPE_FIELD);
		fieldBuilder.setDescription("Тип объекта учета");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("(HOUSE)|(FLAT)|(GARAGE)|(CAR)", "Неизвестный тип объекта учета"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getType()
	{
		return type;
	}
}
