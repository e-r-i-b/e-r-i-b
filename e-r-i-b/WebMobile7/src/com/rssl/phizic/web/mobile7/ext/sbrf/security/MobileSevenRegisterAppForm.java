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

    private static Form createCheckCaptchaMobileSevenRegistrationForm()
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

