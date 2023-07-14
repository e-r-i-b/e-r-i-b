package com.rssl.phizic.web.guest.registration;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareStringValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.forms.validators.login.ExistsLoginValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author usachev
 * @ created 30.01.15
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Форма для регистрации нового гостя
 */
public class CreateGuestProfileForm extends EditFormBase
{
	public static final String LOGIN = "login";
	public static final String PASSWORD = "password";
	public static final String CONFIRM_PASSWORD = "confirmPassword";
	public static final Form FORM = createForm();

	private boolean captcha;
	private boolean success;
	private String type;

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder field = new FieldBuilder();
		field.setName(LOGIN);
		field.setType(StringType.INSTANCE.getName());
		field.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите логин пользователя."),
				new PasswordStrategyValidator("csa_client_login"));
		formBuilder.addField(field.build());

		field = new FieldBuilder();
		field.setName(PASSWORD);
		field.setType(StringType.INSTANCE.getName());
		field.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите пароль."),
				new PasswordStrategyValidator("csa_client_password"));
		formBuilder.addField(field.build());

		field = new FieldBuilder();
		field.setName(CONFIRM_PASSWORD);
		field.setType(StringType.INSTANCE.getName());
		field.addValidators(new RequiredFieldValidator("Введите значение в поле Повторите пароль"));
		formBuilder.addField(field.build());

		CompareStringValidator compareValidator = new CompareStringValidator(true, false);
		compareValidator.setMessage("Логин не должен совпадать с паролем");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, LOGIN);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, PASSWORD);
		formBuilder.addFormValidators(compareValidator);

		compareValidator = new CompareStringValidator(false, false);
		compareValidator.setMessage("Пароли не совпадают");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, PASSWORD);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, CONFIRM_PASSWORD);
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	public boolean getCaptcha()
	{
		return captcha;
	}

	public void setCaptcha(boolean captcha)
	{
		this.captcha = captcha;
	}

	public boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(boolean success)
	{
		this.success = success;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
