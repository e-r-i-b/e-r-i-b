package com.rssl.phizic.web.socialApi.auth.register;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.passwords.SubsequenceLengthValidator;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.CSASocialAPIConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * @author osminin
 * @ created 03.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����������� ����������
 */
public class RegisterAppForm extends ActionFormBase
{
	public static final Form REGISTER_APP_FORM = createRegisterAppForm();
	public static final Form CHECK_CAPTCHA_REGISTRATION_FORM = createCheckCaptchaRegistrationForm();
	public static final Form CONFIRM_REGISTRATION_FORM = createConfirmRegistrationForm();
	public static final Form FINISH_REGISTRATION_FORM = createFinishRegistrationForm();

	// ����������� ����� ������ ��� ����� � ������ ������ ����������
	private Long   minimumPINLength;
	private Long   smsPasswordLifeTime;
	private Long   smsPasswordAttemptsRemain;
	private String password;
	private String mguid;
	private String appType;
	private String captchaBase64String; //����� � Base64

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

		//��� ��������
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("��� ��������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		//�����
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("�����");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_FOUND_USER));
		formBuilder.addField(fb.build());

		//�����
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("������������� ������������");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//��������� 4 ����� ������ �����
		fb = new FieldBuilder();
		fb.setName(Constants.CARD_FIELD);
		fb.setDescription("��������� 4 ����� ������ �����");
		formBuilder.addField(fb.build());
        SocialAPICardValidator socialAPILoginValidator = new SocialAPICardValidator();
        socialAPILoginValidator.setBinding(SocialAPICardValidator.CARD_FIELD, Constants.CARD_FIELD);
		formBuilder.addFormValidators(socialAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createCheckCaptchaRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//�����
		fb.setName("captcha");
		fb.setDescription("�����");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator("������� ��� � ��������."));
		formBuilder.addField(fb.build());

		//��� ��������
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("��� ��������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		//�����
		fb = new FieldBuilder();
		fb.setName(Constants.LOGIN_FIELD);
		fb.setDescription("�����");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_FOUND_USER));
		formBuilder.addField(fb.build());

		//���������� ������������� ����������
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("���������� ������������� ����������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_DEVICE_ID));
		formBuilder.addField(fb.build());

		//��������� 4 ����� ������ �����
		fb = new FieldBuilder();
		fb.setName(Constants.CARD_FIELD);
		fb.setDescription("��������� 4 ����� ������ �����");
		formBuilder.addField(fb.build());

        SocialAPICardValidator socialAPILoginValidator = new SocialAPICardValidator();
        socialAPILoginValidator.setBinding(SocialAPICardValidator.CARD_FIELD, Constants.CARD_FIELD);
        formBuilder.addFormValidators(socialAPILoginValidator);

		return formBuilder.build();
	}

	private static Form createConfirmRegistrationForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//������ �������������
		fb.setName(Constants.SMS_PASSWORD_FIELD);
		fb.setDescription("������ �������������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_SMS_PASSWORD));
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName(Constants.MGUID_FIELD);
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createFinishRegistrationForm()
	{
		CSASocialAPIConfig config = ConfigFactory.getConfig(CSASocialAPIConfig.class);

		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fb = new FieldBuilder();

		//������
		fb = new FieldBuilder();
		fb.setName(Constants.PASSWORD_FIELD);
		fb.setDescription("������");
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

		//���������� ������������� ����������
		fb = new FieldBuilder();
		fb.setName(Constants.CLIENT_ID_FIELD);
		fb.setDescription("���������� ������������� ����������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(Constants.ERROR_DEVICE_ID));
		formBuilder.addField(fb.build());

		//��� ��������
		fb = new FieldBuilder();
		fb.setName(Constants.APP_TYPE_FIELD);
		fb.setDescription("��� ��������");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
