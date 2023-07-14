package com.rssl.phizic.web.persons.formBuilders;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Moshenko
 * Date: 27.05.2013
 * Time: 16:56:06
 */
public class ErmbPersonSearchFormBuilder extends PersonFormBuilderBase
{
	public static final Form SEARCH_BY_PHONE_FORM = new ErmbPersonSearchFormBuilder().buildForm();

	public Form buildForm()
	{
		FormBuilder builder = new FormBuilder();
		builder.addFields(Collections.singletonList(buildField(PHONE_FIELD, PHONE_FIELD_DESCRIPTION, STRING_TYPE_NAME)));
		return builder.build();
	}

	protected void initializeValidators()
	{
       // номер телефона
		List<FieldValidator> validators = new ArrayList<FieldValidator>();
        validators.add( new RegexpFieldValidator("^^(\\+7) \\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})$$", PHONE_FIELD_DESCRIPTION + " введен неверно. Введите 9 или 10 цифр номера без пробелов и разделителей"));
		validators.add( new RequiredFieldValidator());
        fieldValidators.put(PHONE_FIELD, validators);
	}
}

