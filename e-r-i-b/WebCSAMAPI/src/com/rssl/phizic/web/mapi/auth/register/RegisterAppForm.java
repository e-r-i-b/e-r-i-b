package com.rssl.phizic.web.mapi.auth.register;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.passwords.SubsequenceLengthValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.CSAMAPIConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * форма регистрации приложения
 */
public class RegisterAppForm extends ActionFormBase
{
	public static final Form REGISTER_APP_FORM = createRegisterAppForm();
	public static final Form CHECK_CAPTCHA_REGISTRATION_FORM = createCheckCaptchaRegistrationForm();
	public static final Form CONFIRM_REGISTRATION_FORM = createConfirmRegistrationForm();
	public static final Form FINISH_REGISTRATION_FORM = createFinishRegistrationForm();

	// минимальная длина пароля для входа в полную версию приложения
	private Long minimumPINLength;
	private String password;
	private String version;
	private String mguid;
	private String appType;
	private Long smsPasswordLifeTime;
	private Long smsPasswordAttemptsRemain;
	private String captchaBase64String; //капча в Base64

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getMguid()
	{
		return mguid;
	}

	public void setMguid(String mguid)
	{
		this.mguid = mguid;
	}

	public Long getSmsPasswordLifeTime()
	{
		return smsPasswordLifeTime;
	}

	public void setSmsPasswordLifeTime(Long smsPasswordLifeTime)
	{
		this.smsPasswordLifeTime = smsPasswordLifeTime;
	}

	public Long getSmsPasswordAttemptsRemain()
	{
		return smsPasswordAttemptsRemain;
	}

	public void setSmsPasswordAttemptsRemain(Long smsPasswordAttemptsRemain)
	{
		this.smsPasswordAttemptsRemain = smsPasswordAttemptsRemain;
	}

	public String getAppType()
	{
		return appType;
	}

	public void setAppType(String appType)
	{
		this.appType = appType;
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

	private static Form createRegisterAppForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//версия mAPI
		fb.setName(Constants.VERSION_FIELD);
		fb.setDescription("версия мобильного API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип телефона");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		//логин
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_FOUND_USER));
		formBuilder.addField(fb.build());

		//логин
		fb = new FieldBuilder();
		fb.setName(Constants.DEVICE_ID_FIELD);
		fb.setDescription("идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//последние 4 цифры номера карты
		fb = new FieldBuilder();
		fb.setName(Constants.CARD_FIELD);
		fb.setDescription("последние 4 цифры номера карты");
		formBuilder.addField(fb.build());

		MobileAPILoginValidator mobileAPILoginValidator = new MobileAPILoginValidator();
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.VERSION_FIELD, Constants.VERSION_FIELD);
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(mobileAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createCheckCaptchaRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//капча
		fb.setName("captcha");
		fb.setDescription("капча");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("Введите код с картинки."));
		formBuilder.addField(fb.build());

		//версия mAPI
		fb = new FieldBuilder();
		fb.setName(Constants.VERSION_FIELD);
		fb.setDescription("версия мобильного API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип телефона");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		//логин
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_FOUND_USER));
		formBuilder.addField(fb.build());

		//уникальный идентификатор устройства
		fb = new FieldBuilder();
		fb.setName(Constants.DEVICE_ID_FIELD);
		fb.setDescription("уникальный идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_DEVICE_ID));
		formBuilder.addField(fb.build());

		//последние 4 цифры номера карты
		fb = new FieldBuilder();
		fb.setName(Constants.CARD_FIELD);
		fb.setDescription("последние 4 цифры номера карты");
		formBuilder.addField(fb.build());

		MobileAPILoginValidator mobileAPILoginValidator = new MobileAPILoginValidator();
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.VERSION_FIELD, Constants.VERSION_FIELD);
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(mobileAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createConfirmRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//пароль подтверждения
		fb.setName(Constants.SMS_PASSWORD_FIELD);
		fb.setDescription("пароль подтверждения");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_SMS_PASSWORD));
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//версия mAPI
		fb = new FieldBuilder();
		fb.setName(Constants.VERSION_FIELD);
		fb.setDescription("версия мобильного API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createFinishRegistrationForm()
	{
		CSAMAPIConfig config = ConfigFactory.getConfig(CSAMAPIConfig.class);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//пароль
		fb = new FieldBuilder();
		fb.setName(Constants.PASSWORD_FIELD);
		fb.setDescription("Пароль");
		fb.setType(StringType.INSTANCE.getName());
		int mobilePINLength = config.getMobilePINLength();
		fb.addValidators(
				new RequiredFieldValidator(Constants.LOGIN_FIELD),
				new RegexpFieldValidator(config.getMobilePINRegExp(), Constants.ERROR_PASSWORD_SYMBOLS),
				new RegexpFieldValidator(String.format(".{%d,}", mobilePINLength), String.format(Constants.ERROR_PASSWORD_LENGTH, mobilePINLength)),
				new SubsequenceLengthValidator("1234567890", mobilePINLength, Constants.ERROR_PASSWORD_SEQUENCE),
				new SubsequenceRepeateSymbolsValidator(3, Constants.ERROR_PASSWORD_REPEAT)
			);
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//версия mAPI
		fb = new FieldBuilder();
		fb.setName(Constants.VERSION_FIELD);
		fb.setDescription("версия мобильного API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//тип входа мобильного приложения
		fb = new FieldBuilder();
		fb.setName(Constants.IS_LIGHT_SCHEME_FIELD);
		fb.setDescription("тип входа мобильного приложения");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//уникальный идентификатор устройства
		fb = new FieldBuilder();
		fb.setName(Constants.DEVICE_ID_FIELD);
		fb.setDescription("уникальный идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_DEVICE_ID));
		formBuilder.addField(fb.build());

		//тип телефона
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("тип телефона");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		//mobileSdkData
		fb = new FieldBuilder();
		fb.setName(Constants.MOBILE_SDK_DATA_FIELD);
		fb.setDescription("Информация о мобильном устройстве");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		MobileSdkDataRequiredValidator mobileSdkDataRequiredValidator = new MobileSdkDataRequiredValidator();
		mobileSdkDataRequiredValidator.setBinding(MobileSdkDataRequiredValidator.VERSION_FIELD, Constants.VERSION_FIELD);
		mobileSdkDataRequiredValidator.setBinding(MobileSdkDataRequiredValidator.MOBILE_SDK_DATA_FIELD, Constants.MOBILE_SDK_DATA_FIELD);
		formBuilder.addFormValidators(mobileSdkDataRequiredValidator);

		return formBuilder.build();
	}
}
