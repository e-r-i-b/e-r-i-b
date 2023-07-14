package com.rssl.phizic.web.dictionaries.routing.node;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;

/**
 * @author akrenev
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ListNodesForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Адрес");
		fieldBuilder.setName("URL");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
