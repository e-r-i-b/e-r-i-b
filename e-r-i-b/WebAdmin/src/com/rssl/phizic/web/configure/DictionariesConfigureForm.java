package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author Kosyakova
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class DictionariesConfigureForm extends EditFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		Field[] fields = new Field[1];
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("contactDictionariesPath");
		fieldBuilder.setDescription("Каталог для загрузки справочников CONTACT");

		RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
		requiredFieldValidator.setMessage("Укажите значение в поле: " + fieldBuilder.getDescription());
		fieldBuilder.addValidators(new FieldValidator[]{requiredFieldValidator});

		fields[0] = fieldBuilder.build();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}

