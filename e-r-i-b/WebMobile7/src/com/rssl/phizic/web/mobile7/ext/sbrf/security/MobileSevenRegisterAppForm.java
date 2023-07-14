package com.rssl.phizic.web.mobile7.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.web.common.mobile.ext.sbrf.security.RegisterAppForm;

/**
 * @author sergunin
 * @ created 05.06.14
 * @ $Author$
 * @ $Revision$
 */

public class MobileSevenRegisterAppForm extends RegisterAppForm
{
    public static final Form START_MOBILE_SEVEN_REGISTRATION_FORM         = createMobileSevenRegistrationForm();
    public static final Form CHECK_CAPTCHA_MOBILE_SEVEN_REGISTRATION_FORM = createCheckCaptchaMobileSevenRegistrationForm();

    private static Form createMobileSevenRegistrationForm()
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

    private static Form createCheckCaptchaMobileSevenRegistrationForm()
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

