package com.rssl.phizic.web.auth.recover;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareStringValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.web.auth.AuthStageFormBase;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RecoverPasswordForm extends AuthStageFormBase
{
	public static final Form PRE_CONFIRM_FORM   = getPreForm();
	public static final Form CONFIRM_FORM       = getConfirmForm();
	public static final Form POST_CONFIRM_FORM  = createPostForm();

	private int hintDelay;

	public String getLogin()
	{
		return (String) getFields().get(LOGIN_FIELD_NAME);
	}

	private static Form getPreForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(LOGIN_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Логин");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите идентификатор или логин пользователя."),
				new RegexpFieldValidator(".{5,30}", "Пожалуйста, введите идентификатор или логин пользователя не менее 5, но не более 30 символов."));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form getConfirmForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("SMS-пароль");
		fb.addValidators(new RequiredFieldValidator("Введите пароль, полученный через sms"));
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}

	private static Form createPostForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(PASSWORD_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Новый пароль");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите новый пароль."),
				new PasswordStrategyValidator("csa_client_password"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Повторите новый пароль");
		formBuilder.addField(fb.build());

		CompareStringValidator compareValidator = new CompareStringValidator(false, false);
		compareValidator.setMessage("Пароли не совпадают");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, PASSWORD_FIELD_NAME);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, CONFIRM_PASSWORD_FIELD);
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public int getHintDelay()
	{
		return hintDelay;
	}

	public void setHintDelay(int hintDelay)
	{
		this.hintDelay = hintDelay;
	}
}
