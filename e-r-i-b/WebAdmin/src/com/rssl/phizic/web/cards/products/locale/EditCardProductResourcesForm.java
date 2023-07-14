package com.rssl.phizic.web.cards.products.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * Форма редактирования мультиязычных текстовок
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCardProductResourcesForm extends EditLanguageResourcesBaseForm
{
	static final Form EDIT_FORM = createForm();

	public static final String NAME_FIELD = "name";

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName(NAME_FIELD);
		fieldBuilder.setType("string");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,255}", "Наименование должно быть не более 255 символов"));
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
