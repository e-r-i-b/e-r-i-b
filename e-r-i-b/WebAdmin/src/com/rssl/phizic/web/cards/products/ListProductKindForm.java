package com.rssl.phizic.web.cards.products;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.types.StringType;

/**
 * @author gulov
 * @ created 07.10.2011
 * @ $Authors$
 * @ $Revision$
 */
public class ListProductKindForm extends ListFormBase
{
	static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName("kindName");
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
