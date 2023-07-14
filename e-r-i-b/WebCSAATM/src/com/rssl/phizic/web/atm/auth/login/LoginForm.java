package com.rssl.phizic.web.atm.auth.login;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.common.types.atm.Constants;
import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * @author osminin
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Форма входа пользователя в АТМ
 */
public class LoginForm extends ActionFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		fb = new FieldBuilder();
		fb.setName(Constants.PAN_FIELD);
		fb.setDescription("Карта входа");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_MESSAGE), new RegexpFieldValidator("\\d+", Constants.ERROR_MESSAGE));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
