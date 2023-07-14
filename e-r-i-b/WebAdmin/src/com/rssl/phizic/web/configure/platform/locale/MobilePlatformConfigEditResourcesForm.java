package com.rssl.phizic.web.configure.platform.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 13.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class MobilePlatformConfigEditResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createForm();

	@SuppressWarnings("TooBroadScope")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformName");
		fieldBuilder.setDescription("Название платформы");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,100}", "Название должно быть не более 100 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("errText");
		fieldBuilder.setDescription("Текст ошибки, отображаемый при несовместимости с поддерживаемой версией");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,500}", "Текст ошибки должен быть не более 500 символов.")
		);
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
