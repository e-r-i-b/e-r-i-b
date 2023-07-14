package com.rssl.phizic.web.errors;

import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author gladishev
 * @ created 16.11.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListErrorMessageForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// –егул€рное выражение
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("–егул€рное выражение");
		fieldBuilder.setName("regExp");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(
				new RegexpFieldValidator(".{1,256}", "–егул€рное выражение должно быть не более 256 символов")
		);

		fb.addField(fieldBuilder.build());

		//“ип ошибки
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("“ип ошибки");
		fieldBuilder.setName("errorType");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		//—истема
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("—истема");
		fieldBuilder.setName("system");
		fieldBuilder.setType("string");

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
