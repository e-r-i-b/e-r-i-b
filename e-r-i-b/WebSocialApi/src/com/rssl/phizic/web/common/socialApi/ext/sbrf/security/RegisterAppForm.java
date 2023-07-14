package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.SocialAPICardValidator;
import com.rssl.common.forms.validators.SubsequenceRepeateSymbolsValidator;
import com.rssl.common.forms.validators.passwords.SubsequenceLengthValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.security.LoginForm;

/**
 * @author Pankin
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 * форма регистрации социального приложения
 */

public class RegisterAppForm extends LoginForm
{
	protected static final String ERROR_LOGIN_MESSAGE = StrutsUtils.getMessage("error.login.failed", "securityBundle");

	public static final Form START_SOCIAL_REGISTRATION_FORM         = createSocialRegistrationForm();
	public static final Form CHECK_CAPTCHA_SOCIAL_REGISTRATION_FORM = createCheckCaptchaSocialRegistrationForm();
	public static final Form CONFIRM_SOCIAL_REGISTRATION_FORM       = createConfirmSocialRegistrationForm();
	public static final Form SET_PIN_SOCIAL_REGISTRATION_FORM       = createSetPinSocialRegistrationForm();

	protected static final String ERROR_PASSWORD_SYMBOLS              = "Код содержит недопустимые символы.";
	protected static final String ERROR_PASSWORD_LENGTH               = "Код должен содержать не менее %d символов.";
	protected static final String ERROR_PASSWORD_SEQUENCE             = "Код не может быть последовательным (12345, 23456, 76543).";
	protected static final String ERROR_PASSWORD_REPEAT               = "Код не может содержать более трех одинаковых цифр подряд (33334, 55555).";

	protected static final String ERROR_SMS_PASSWORD = "Введите SMS-пароль.";

	private String socialPassword; // пароль для полного доступа в социальное приложении
	private String confirmPassword; // пароль для подтверждения регистрации
	private Long minimumPINLength; // минимальная длина пароля для входа в полную версию приложения

	//out
	private String captchaBase64String; //капча в Base64

	public String getSocialPassword()
	{
		return socialPassword;
	}

	public void setSocialPassword(String socialPassword)
	{
		this.socialPassword = socialPassword;
	}

	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	public Long getMinimumPINLength()
	{
		return minimumPINLength;
	}

	public void setMinimumPINLength(Long minimumPINLength)
	{
		this.minimumPINLength = minimumPINLength;
	}

	public String getCaptchaBase64String()
	{
		return captchaBase64String;
	}

	public void setCaptchaBase64String(String captchaBase64String)
	{
		this.captchaBase64String = captchaBase64String;
	}

	private static Form createSocialRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип телефона");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
		formBuilder.addField(fb.build());

		//логин
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
		formBuilder.addField(fb.build());

		//идентификатор устройства
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("уникальный идентификатор пользователя");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

        //последние 4 цифры карты
        fb = new FieldBuilder();
        fb.setName(Constants.CARD_FIELD);
        fb.setDescription("Последние 4 цифры карты клиента");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

		SocialAPICardValidator socialAPILoginValidator = new SocialAPICardValidator();
		socialAPILoginValidator.setBinding(SocialAPICardValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(socialAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createCheckCaptchaSocialRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//капча
		fb.setName(Constants.CAPTCHA_FIELD);
		fb.setDescription("капча");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Введите код с картинки."));
		formBuilder.addField(fb.build());

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип телефона");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
		formBuilder.addField(fb.build());

		//логин
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
		formBuilder.addField(fb.build());

		//идентификатор устройства
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("уникальный идентификатор пользователя");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

        //последние 4 цифры карты
        fb = new FieldBuilder();
        fb.setName(Constants.CARD_FIELD);
        fb.setDescription("Последние 4 цифры карты клиента");
        fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		SocialAPICardValidator socialAPILoginValidator = new SocialAPICardValidator();
		socialAPILoginValidator.setBinding(SocialAPICardValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(socialAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createConfirmSocialRegistrationForm()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//пароль подтверждения
		fb.setName(Constants.SMS_PASSWORD_FIELD);
		fb.setDescription("пароль подтверждения");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_SMS_PASSWORD));
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип приложения");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createSetPinSocialRegistrationForm()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//appType
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("appType");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//пароль
		fb = new FieldBuilder();
		fb.setName(Constants.PASSWORD_FIELD);
		fb.setDescription("Пароль");
		fb.setType(StringType.INSTANCE.getName());
		int mobilePINLength = securityConfig.getMobilePINLength();
		fb.addValidators(
				new RequiredFieldValidator(ERROR_LOGIN_MESSAGE),
				new RegexpFieldValidator(securityConfig.getMobilePINRegExp(), ERROR_PASSWORD_SYMBOLS),
				new RegexpFieldValidator(String.format(".{%d,}", mobilePINLength), String.format(ERROR_PASSWORD_LENGTH, mobilePINLength)),
				new SubsequenceLengthValidator("1234567890", mobilePINLength, ERROR_PASSWORD_SEQUENCE),
				new SubsequenceRepeateSymbolsValidator(3, ERROR_PASSWORD_REPEAT)
			);
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//идентификатор устройства
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("уникальный идентификатор пользователя");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
