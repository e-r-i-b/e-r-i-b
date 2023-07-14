package com.rssl.phizic.web.dictionaries.pfp.targets;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPImageEditFormBase;

/**
 * @author akrenev
 * @ created 21.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditTargetForm extends PFPImageEditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,250}", "Поле Название должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("onlyOne");
		fieldBuilder.setDescription("Возможность добавлять только одну цель данного типа");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("laterAll");
		fieldBuilder.setDescription("Дата достижения цели позже остальных");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("laterLoans");
		fieldBuilder.setDescription("Дата достижения цели позже последнего платежа по кредиту");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
