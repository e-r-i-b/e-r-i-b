package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.image.ImageEditFormBase;

/**
 * @author akrenev
 * @ created 03.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceCompanyForm extends ImageEditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование страховой компании");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator("Пожалуйста, введите название страховой компании."));
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,50}", "Поле \"Наименование страховой компании\" должно содержать не более 50 символов."));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		return formBuilder.build();
	}
}
