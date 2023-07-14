package com.rssl.phizic.web.dictionaries.billing;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author akrenev
 * @ created 10.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListBillingsForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");


		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор");
		fieldBuilder.setName("code");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
