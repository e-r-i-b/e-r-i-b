package com.rssl.phizic.web.dictionaries.regions.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditRegionResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = getEditForm();

	private static Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Поле Наименование не должно превышать 128 символов")
		);
		fieldBuilder.clearValidators();

		return formBuilder.build();
	}

}
