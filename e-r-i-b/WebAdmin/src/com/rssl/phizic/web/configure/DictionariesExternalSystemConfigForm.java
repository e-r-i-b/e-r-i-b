package com.rssl.phizic.web.configure;

import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author osminin
 * @ created 19.05.2009
 * @ $Author$
 * @ $Revision$
 */

public class DictionariesExternalSystemConfigForm extends FilterActionForm
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("externalSystem");
		fieldBuilder.setDescription("Наименование адаптера");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("externalSystemId");
		fieldBuilder.setDescription("Идентификатор адаптера");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(new RequiredFieldValidator("Выберите адаптер"));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
