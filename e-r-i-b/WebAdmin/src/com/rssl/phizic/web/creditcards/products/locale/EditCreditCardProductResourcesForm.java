package com.rssl.phizic.web.creditcards.products.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

import java.math.BigInteger;

/**
 * Форма редактирования мультиязычных текстовок
 * @author komarov
 * @ created 22.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditCreditCardProductResourcesForm extends EditLanguageResourcesBaseForm
{
	static final Form EDIT_FORM = createForm();

	public static final String NAME_FIELD = "name";
	public static final String ADDITIONAL_TERMS_FIELD = "additionalTerms";

	@SuppressWarnings("ReuseOfLocalVariable")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Наименование продукта");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,100}", "Наименование продукта должно быть не более 100 символов"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(ADDITIONAL_TERMS_FIELD);
		fieldBuilder.setDescription("Дополнительные условия");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		LengthFieldValidator lengthValidator = new LengthFieldValidator();
		lengthValidator.setParameter("maxlength", new BigInteger("500"));
		lengthValidator.setMessage("Дополнительные условия должны быть не более 500 символов");
		fieldBuilder.addValidators(lengthValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
