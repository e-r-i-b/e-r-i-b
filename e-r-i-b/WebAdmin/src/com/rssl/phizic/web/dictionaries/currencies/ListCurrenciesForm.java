package com.rssl.phizic.web.dictionaries.currencies;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCurrenciesForm extends ListFormBase
{
	public static final Form FILTER_FORM = createEditForm();
	
	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("code");
		fieldBuilder.setDescription("Код валюты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,3}", "Поле Код валюты должно содержать не более 3 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название валюты");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,3}", "Поле Название валюты должно содержать не более 3 символов."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
