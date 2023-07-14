package com.rssl.phizic.web.autopayments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * @author bogdanov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

public class EditAutoPaymentsP2PNewCommissionForm extends EditPropertiesFormBase
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
		fb.setName("com.rssl.phizia.autopayments.p2p.getCommissionFromWay4");
		fb.setDescription("Переключатель способа расчета комиссии при подключении автоперевода P2P");
		fb.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
