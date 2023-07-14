package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author komarov
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListInformMessagesTemplateForm extends ListInformMessagesFormBase
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = createFilter();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,40}", "Размер поля должен быть не более 40 символов")
		);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
