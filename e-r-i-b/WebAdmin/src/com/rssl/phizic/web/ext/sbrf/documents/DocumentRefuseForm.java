package com.rssl.phizic.web.ext.sbrf.documents;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author hudyakov
 * @ created 24.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class DocumentRefuseForm extends EditFormBase
{
	public static final Form REFUSE_FORM = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина отказа");
		fieldBuilder.setName("reason");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators
		(
			new RequiredFieldValidator(),
			new RegexpFieldValidator(".{0,255}", "Поле Причина отказа не должно превышать 255 символов.")
		);

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
