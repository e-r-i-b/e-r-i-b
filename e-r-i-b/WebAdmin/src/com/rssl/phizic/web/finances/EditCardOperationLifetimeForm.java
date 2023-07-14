package com.rssl.phizic.web.finances;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.config.ips.IPSConstants;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * форма настройки времени жизни операций
 * @author Jatsky
 * @ created 30.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditCardOperationLifetimeForm extends EditPropertiesFormBase
{
	private static final Form EDIT_FORM = createForm();

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}

	private static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Время жизни авторизаций");
		fieldBuilder.setName(IPSConstants.CARD_OPERATION_LIFETIME);
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(requiredValidator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
