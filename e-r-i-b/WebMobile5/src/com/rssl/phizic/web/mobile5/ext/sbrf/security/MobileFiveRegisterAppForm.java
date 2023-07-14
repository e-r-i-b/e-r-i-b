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
 * ����� ����������� ���������� ���������� (��� ������ 5.0)
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

        //������ �������������
        fb.setName("smsPassword");
        fb.setDescription("������ �������������");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator(ERROR_SMS_PASSWORD));
        formBuilder.addField(fb.build());

        //������
        fb = new FieldBuilder();
        fb.setName("password");
        fb.setDescription("������");
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

        //������ mAPI
        fb = new FieldBuilder();
        fb.setName("version");
        fb.setDescription("������ ���������� API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //��� ����� ���������� ����������
        fb = new FieldBuilder();
        fb.setName("isLightScheme");
        fb.setDescription("��� ����� ���������� ����������");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //��� ����������
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("appType");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }

    private static Form createMobileFiveRegistrationForm()
    {
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fb = new FieldBuilder();

        //������ mAPI
        fb.setName("version");
        fb.setDescription("������ ���������� API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //��� ��������
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("��� ��������");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
        formBuilder.addField(fb.build());

        //�����
        fb = new FieldBuilder();
        fb.setName("login");
        fb.setDescription("�����");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
        formBuilder.addField(fb.build());

        //������������� ����������
        fb = new FieldBuilder();
        fb.setName(Constants.DEVICE_ID_FIELD);
        fb.setDescription("���������� ������������� ����������");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }

    private static Form createCheckCaptchaMobileFiveRegistrationForm()
    {
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fb = new FieldBuilder();

        //�����
        fb.setName("captcha");
        fb.setDescription("�����");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������� ��� � ��������."));
        formBuilder.addField(fb.build());

        //������ mAPI
        fb = new FieldBuilder();
        fb.setName("version");
        fb.setDescription("������ ���������� API");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        //��� ��������
        fb = new FieldBuilder();
        fb.setName("appType");
        fb.setDescription("��� ��������");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
        formBuilder.addField(fb.build());

        //�����
        fb = new FieldBuilder();
        fb.setName("login");
        fb.setDescription("�����");
        fb.setType(StringType.INSTANCE.getName());
        fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
        formBuilder.addField(fb.build());

        //������������� ����������
        fb = new FieldBuilder();
        fb.setName(Constants.DEVICE_ID_FIELD);
        fb.setDescription("���������� ������������� ����������");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

        return formBuilder.build();
    }
}
