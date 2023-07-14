package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.security.LoginForm;
import com.rssl.phizic.web.actions.StrutsUtils;

/**
 * Форма логина 
 * @author Pankin
 * @ created 11.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginATMForm extends LoginForm
{
	private static final String ERROR_MESSAGE = StrutsUtils.getMessage("error.login.failed", "securityBundle");

	public static final Form ATM_LOGIN_FORM = createForm();

	@SuppressWarnings({"ReuseOfLocalVariable"})
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb = new FieldBuilder();
		fb.setName("pan");
		fb.setDescription("Карта входа");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_MESSAGE), new RegexpFieldValidator("\\d+", ERROR_MESSAGE));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("codeATM");
		fb.setDescription("Номер устройства самообслуживания");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_MESSAGE));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
