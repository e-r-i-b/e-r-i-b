package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author tisov
 * @ created 12.09.14
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoPaymentsNearestDateRestrictionForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createEditForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("com.rssl.phizia.autopayments.isNearestDateRestrictionActive");
		fb.setDescription("Ограничения по удалённости даты ближайшего перевода для P2P автоплатежей и копилки");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
