package com.rssl.phizic.web.common.dictionaries.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings("TooBroadScope")
public class EditBankResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = getEditForm();

	private static Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.addValidators (
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,256}", "Наименование банка не должно превышать 256 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName("place");
		fieldBuilder.setDescription("Местонахождение");
		fieldBuilder.addValidators (
				new RegexpFieldValidator(".{1,30}","Местонахождение банка не должно превышать 30 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName("shortName");
		fieldBuilder.setDescription("Краткое наименование");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,30}", "Краткое наименование банка не должно превышать 30 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName("address");
		fieldBuilder.setDescription("Адрес");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{1,30}", "Адрес банка не должен превышать 256 символов")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
