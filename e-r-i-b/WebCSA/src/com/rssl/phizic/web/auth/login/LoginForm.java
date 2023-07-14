package com.rssl.phizic.web.auth.login;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.auth.AuthStageFormBase;

/**
 * @author niculichev
 * @ created 26.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginForm extends AuthStageFormBase
{
	public static final String CONNECTOR_GUID = "connectorGuid";

	public static final Form LOGIN_FORM = createLoginForm();
	public static final Form CHOICE_LOGINS_FORM = createChoiceLoginsForm();

	private static Form createLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(LOGIN_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Идентификатор");
		fb.addValidators(new RequiredFieldValidator("Пожалуйста, введите идентификатор или логин пользователя."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(PASSWORD_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Пароль");
		fb.addValidators(new RequiredFieldValidator("Пожалуйста, введите пароль."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(NEED_TURING_TEST);
		fb.setType("boolean");
		fb.setDescription("Необходимо ли отображать каптчу.");
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createChoiceLoginsForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CONNECTOR_GUID);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Идентификатор");
		fb.addValidators(new RequiredFieldValidator("Выберите идентификатор"));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
