package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInsuranceProductForm extends ListFormBase
{
	public static final Form FILTER_FORM = createEditForm();

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forComplex");
		fieldBuilder.setDescription("Доступность клиенту");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
