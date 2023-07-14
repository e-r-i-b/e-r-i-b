package com.rssl.phizic.web.dictionaries.pages.messages;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author komarov
 * @ created 27.09.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListInformMessagesFormBase extends ListFormBase
{
	protected static FormBuilder createFilter()
	{
		FormBuilder fb = new FormBuilder();
		// Название
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ключевое слово");
		fieldBuilder.setName("keyWord");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,40}", "Размер поля должен быть не более 40 символов")
		);
		fb.addField(fieldBuilder.build());
		return fb;
	}
}
