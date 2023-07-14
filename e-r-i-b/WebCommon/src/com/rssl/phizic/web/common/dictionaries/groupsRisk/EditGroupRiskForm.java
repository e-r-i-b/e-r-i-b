package com.rssl.phizic.web.common.dictionaries.groupsRisk;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author basharin
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 * форма редактирования группы риска
 */

public class EditGroupRiskForm extends EditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	protected static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("name");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Название");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("isDefault");
		fb.setType(BooleanType.INSTANCE.getName());
		fb.setDescription("По умолчанию");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("rank");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Степень риска");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}