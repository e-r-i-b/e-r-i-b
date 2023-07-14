package com.rssl.phizic.web.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.StrutsUtils;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 20.09.2005 Time: 14:17:58 */
public class LoginForm extends ActionFormBase
{
	private static final String ERROR_LOGIN_MESSAGE = StrutsUtils.getMessage("error.login.failed", "securityBundle");

	public static final Form LOGIN_FORM                     = createLoginForm();
	public static final Form MOBILE_LOGIN_FORM              = createMobileLoginForm();
	public static final Form MOBILE_LOGIN_BY_MGUID_FORM     = createMobileLoginByMGUIDForm();
	public static final Form SOCIAL_LOGIN_BY_MGUID_FORM     = createSocialLoginByMGUIDForm();

	private String login;                       //логин
    private String password;                    //пароль
	private String accessType;
	private String version;                     // для мобильного API. Версия самого API
	private String mguid;
	private String appType;
	private String deviceName;
	private Long smsPasswordLifeTime;           // оставшееся время жизни СМС-пароля
	private Long smsPasswordAttemptsRemain;     // оставшееся число попыток ввода СМС-пароля

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

	public String getAccessType()
	{
		return accessType;
	}

	public void setAccessType(String accessType)
	{
		this.accessType = accessType;
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

	public String getAppType()
	{
		return appType;
	}

	public void setAppType(String appType)
	{
		this.appType = appType;
	}

	public String getDeviceName()
	{
		return deviceName;
	}

	public void setDeviceName(String deviceName)
	{
		this.deviceName = deviceName;
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

	private static Form createLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		//логин
		fb = new FieldBuilder();
		fb.setName("login");
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_LOGIN_MESSAGE));
		formBuilder.addField(fb.build());

		//пароль
		fb = new FieldBuilder();
		fb.setName("password");
		fb.setDescription("пароль");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_LOGIN_MESSAGE));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createMobileLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		//логин
		fb = new FieldBuilder();
		fb.setName("login");
		fb.setDescription("логин");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_LOGIN_MESSAGE));
		formBuilder.addField(fb.build());

		//пароль
		fb = new FieldBuilder();
		fb.setName("password");
		fb.setDescription("пароль");
		fb.setType(StringType.INSTANCE.getName());
		fb.addValidators(new RequiredFieldValidator(ERROR_LOGIN_MESSAGE));
		formBuilder.addField(fb.build());

		//версия mAPI
		fb = new FieldBuilder();
		fb.setName("version");
		fb.setDescription("версия мобильного API");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	private static Form createMobileLoginByMGUIDForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();

		//appType
		fb = new FieldBuilder();
		fb.setName("appType");
		fb.setDescription("appType");
		fb.setType(StringType.INSTANCE.getName());
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

		//идентификатор устройства
		fb = new FieldBuilder();
		fb.setName("devID");
		fb.setDescription("уникальный идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
	private static Form createSocialLoginByMGUIDForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();

		//appType
		fb = new FieldBuilder();
		fb.setName("appType");
		fb.setDescription("appType");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//mGUID
		fb = new FieldBuilder();
		fb.setName("mGUID");
		fb.setDescription("mGUID");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		//идентификатор клиента
		fb = new FieldBuilder();
		fb.setName("extClientID");
		fb.setDescription("уникальный идентификатор устройства");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
