package com.rssl.phizic.web.dictionaries.pfp.products.simple.pension.fund;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPImageEditFormBase;

/**
 * @author akrenev
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования пенсионного фонда
 */

public class EditPensionFundForm extends PFPImageEditFormBase
{
	public static final String NAME_FIELD_NAME = "name";

	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(NAME_FIELD_NAME);
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,50}", "Поле Название должно содержать не более 250 символов."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		return formBuilder.build();
	}

}
