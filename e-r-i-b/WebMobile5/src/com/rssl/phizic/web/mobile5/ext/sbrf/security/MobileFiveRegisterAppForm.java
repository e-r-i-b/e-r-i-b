package com.rssl.phizic.web.mobile5.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.SubsequenceRepeateSymbolsValidator;
import com.rssl.common.forms.validators.passwords.SubsequenceLengthValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.web.common.mobile.ext.sbrf.security.RegisterAppForm;

/**
 * форма регистрации мобильного приложения (для версии 5.0)
 * @author Jatsky
 * @ created 13.08.13
 * @ $Author$
 * @ $Revision$
 */

public class MobileFiveRegisterAppForm extends RegisterAppForm
{
	public static final Form CONFIRM_MOBILE_REGISTRATION_FORM_OLD = createConfirmMobileRegistrationOldForm();
    public static final Form START_MOBILE_FIVE_REGISTRATION_FORM         = createMobileFiveRegistrationForm();
    public static final Form CHECK_CAPTCHA_MOBILE_FIVE_REGISTRATION_FORM = createCheckCaptchaMobileFiveRegistrationForm();

	private static Form createConfirmMobileRegistrationOldForm()
    {
        SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fb = new FieldBuilder();

        //пароль подтверждения
        fb.setName("smsPassword");
        fb.setDescription("пароль подтверждения");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator(ERROR_SMS_PASSWORD));
        formBuilder.addField(fb.build());

        //пароль
        fb = new FieldBuilder();
        fb.setName("password");
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
        fb.setName("mGUID");
        fb.setDescription("mGUID");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //версия mAPI
        fb = new FieldBuilder();
        fb.setName("version");
        fb.setDescription("версия мобильного API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //тип входа мобильного приложения
        fb = new FieldBuilder();
        fb.setName("isLightScheme");
        fb.setDescription("тип входа мобильного приложения");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //тип приложения
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("appType");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }

    private static Form createMobileFiveRegistrationForm()
    {
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fb = new FieldBuilder();

        //версия mAPI
        fb.setName("version");
        fb.setDescription("версия мобильного API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //тип телефона
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("тип телефона");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
        formBuilder.addField(fb.build());

        //логин
        fb = new FieldBuilder();
        fb.setName("login");
        fb.setDescription("логин");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
        formBuilder.addField(fb.build());

        //идентификатор устройства
        fb = new FieldBuilder();
        fb.setName(Constants.DEVICE_ID_FIELD);
        fb.setDescription("уникальный идентификатор устройства");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }

    private static Form createCheckCaptchaMobileFiveRegistrationForm()
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
        fb.setName("version");
        fb.setDescription("версия мобильного API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //тип телефона
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("тип телефона");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
        formBuilder.addField(fb.build());

        //логин
        fb = new FieldBuilder();
        fb.setName("login");
        fb.setDescription("логин");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор."));
        formBuilder.addField(fb.build());

        //идентификатор устройства
        fb = new FieldBuilder();
        fb.setName(Constants.DEVICE_ID_FIELD);
        fb.setDescription("уникальный идентификатор устройства");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }
}
