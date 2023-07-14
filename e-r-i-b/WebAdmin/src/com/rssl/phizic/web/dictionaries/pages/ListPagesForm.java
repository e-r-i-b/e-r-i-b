package com.rssl.phizic.web.dictionaries.pages;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 01.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListPagesForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		// Название
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,64}", "Название страницы должно быть не более 64 символов")
		);
		fb.addField(fieldBuilder.build());
		//родительский элемент
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Родительский элемент");
		fieldBuilder.setName("parentId");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());
		return fb.build();
	}
}
