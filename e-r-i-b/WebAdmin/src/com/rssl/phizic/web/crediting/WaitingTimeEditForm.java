package com.rssl.phizic.web.crediting;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * Форма настройки времени ожидания получения предодобренных предложений из CRM
 * @author Nady
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 */
public class WaitingTimeEditForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("com.rssl.iccs.crediting.waitingTime");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setDescription("Время ожидания");
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}
}
