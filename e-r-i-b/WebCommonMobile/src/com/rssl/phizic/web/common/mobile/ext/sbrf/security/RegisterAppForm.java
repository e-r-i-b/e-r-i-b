package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.MobileAPILoginValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
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
 * ����� ����������� ���������� ����������
 */

public class RegisterAppForm extends LoginForm
{
	protected static final String ERROR_LOGIN_MESSAGE = StrutsUtils.getMessage("error.login.failed", "securityBundle");

	public static final Form START_MOBILE_REGISTRATION_FORM         = createMobileRegistrationForm();
	public static final Form CHECK_CAPTCHA_MOBILE_REGISTRATION_FORM = createCheckCaptchaMobileRegistrationForm();
	public static final Form CONFIRM_MOBILE_REGISTRATION_FORM       = createConfirmMobileRegistrationForm();
	public static final Form SET_PIN_MOBILE_REGISTRATION_FORM       = createSetPinMobileRegistrationForm();

	protected static final String ERROR_PASSWORD_SYMBOLS              = "��� �������� ������������ �������.";
	protected static final String ERROR_PASSWORD_LENGTH               = "��� ������ ��������� �� ����� %d ��������.";
	protected static final String ERROR_PASSWORD_SEQUENCE             = "��� �� ����� ���� ���������������� (12345, 23456, 76543).";
	protected static final String ERROR_PASSWORD_REPEAT               = "��� �� ����� ��������� ����� ���� ���������� ���� ������ (33334, 55555).";

	protected static final String ERROR_SMS_PASSWORD = "������� SMS-������.";

	private String mobilePassword; // ������ ��� ������� ������� � ��������� ����������
	private String confirmPassword; // ������ ��� ������������� �����������
	private Long minimumPINLength; // ����������� ����� ������ ��� ����� � ������ ������ ����������

	//out
	private String captchaBase64String; //����� � Base64

	public String getMobilePassword()
	{
		return mobilePassword;
	}

	public void setMobilePassword(String mobilePassword)
	{
		this.mobilePassword = mobilePassword;
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

	private static Form createMobileRegistrationForm()
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

        //��������� 4 ����� �����
        fb = new FieldBuilder();
        fb.setName(Constants.CARD_FIELD);
        fb.setDescription("��������� 4 ����� ����� �������");
        fb.setType(StringType.INSTANCE.getName());
        formBuilder.addField(fb.build());

		MobileAPILoginValidator mobileAPILoginValidator = new MobileAPILoginValidator();
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.VERSION_FIELD, Constants.VERSION_FIELD);
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(mobileAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createCheckCaptchaMobileRegistrationForm()
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

        //��������� 4 ����� �����
        fb = new FieldBuilder();
        fb.setName(Constants.CARD_FIELD);
        fb.setDescription("��������� 4 ����� ����� �������");
        fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		MobileAPILoginValidator mobileAPILoginValidator = new MobileAPILoginValidator();
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.VERSION_FIELD, Constants.VERSION_FIELD);
		mobileAPILoginValidator.setBinding(MobileAPILoginValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(mobileAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createConfirmMobileRegistrationForm()
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

		//��� ��������
		fb = new FieldBuilder();
		fb.setName("appType");
		fb.setDescription("��� ��������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������."));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createSetPinMobileRegistrationForm()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//appType
		fb = new FieldBuilder();
		fb.setName("appType");
		fb.setDescription("appType");
		fb.setType(StringType.INSTANCE.getName());
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

		//������������� ����������
		fb = new FieldBuilder();
		fb.setName(Constants.DEVICE_ID_FIELD);
		fb.setDescription("���������� ������������� ����������");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
