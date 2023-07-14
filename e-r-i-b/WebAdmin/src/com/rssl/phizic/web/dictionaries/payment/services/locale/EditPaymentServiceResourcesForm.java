package com.rssl.phizic.web.dictionaries.payment.services.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author mihaylov
 * @ created 23.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования локалезависмых данных услуги
 */
public class EditPaymentServiceResourcesForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("localedName");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,128}", "Наименование должно быть не более 128 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Описание");
		fieldBuilder.setName("localedDescription");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{1,512}", "Описание должно быть не более 512 символов."));
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
