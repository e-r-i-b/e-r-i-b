package com.rssl.phizic.web.ext.sbrf.technobreaks.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.ext.sbrf.technobreaks.Constants;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author koptyaev
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditTechnoBreakResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(Constants.MESSAGE);
		fb.setDescription("Сообщение");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("(?s).{0,200}", "Сообщение должно содержать не более 200 символов"));
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}
}
