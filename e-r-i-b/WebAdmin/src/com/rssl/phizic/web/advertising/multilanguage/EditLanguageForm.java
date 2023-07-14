package com.rssl.phizic.web.advertising.multilanguage;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.advertising.Constants;
import com.rssl.phizic.business.dictionaries.url.validators.WhiteListUrlForTextValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;
import com.rssl.phizic.web.validators.LengthWithoutTagFieldValidator;

import java.math.BigInteger;

/**
 * @author komarov
 * @ created 23.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLanguageForm extends EditLanguageResourcesBaseForm
{
	public static final Form EDIT_LANGUAGE_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Заголовок баннера");
		fieldBuilder.setName("title");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RegexpFieldValidator("(?s).{0,100}", "Заголовок баннера должен быть не более 100 символов."),
				new RequiredFieldValidator("Заполните поле Заголовок баннера.")
		);
		formBuilder.addField(fieldBuilder.build());

		//noinspection ReuseOfLocalVariable
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Текст баннера");
		fieldBuilder.setName("text");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators (
				new RegexpFieldValidator("(?s).{0,400}", "Текст баннера должен быть не более 400 символов."),
				new WhiteListUrlForTextValidator(),
				new RequiredFieldValidator("Заполните поле Текст баннера.")
		);
		formBuilder.addField(fieldBuilder.build());

		LengthWithoutTagFieldValidator lengthValidator = new LengthWithoutTagFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("20"));
		lengthValidator.setMessage("Название кнопки 20 символов");
		for(int i = 0; i < Constants.NUMBER_OF_BUTTONS ; i++)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setDescription("Название кнопки");
			fieldBuilder.setName("buttonTitle"+i);
			fieldBuilder.setType("string");
			fieldBuilder.addValidators (lengthValidator);
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
