package com.rssl.phizic.web.payments;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author hudyakov
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentRefuseForm extends EditFormBase
{
	public static final Form REFUSE_FORM     = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("refuseReason");
		fieldBuilder.setType("string");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина отказа");
		fieldBuilder.setName("selectedRefuseReason");
		fieldBuilder.setType("string");

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Другое");
		fieldBuilder.setName("otherReason");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}