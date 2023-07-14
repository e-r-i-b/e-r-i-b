package com.rssl.phizic.security.config;

import com.rssl.common.forms.validators.passwords.PasswordValidationConfig;
import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

import static com.rssl.phizic.config.csa.Constants.*;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 15:18:15
 */
public class SimpleSecurityConfig extends SecurityConfig
{
    private Application application;
	private String  permissionProvider;
	private String  guestPermissionProvider;
	private String  adminPermissionProvider;
	private String  permissionCalculator;
	private String  loginInfoProvider;
    private String  superUserName;
    private int     loginAttempts;
	private int     passwordLifeTime;
    private int     cardPasswords;
    private int     blockedTimeout;
    private int     confirmAttempts;
    private int     passwordLength;
    private int     cardsCount;
    private String  passwordAllowedChars;
    private int     cardPasswordLength;
    private String  cardPasswordAllowedChars;
	private String  loginAllowedChars;
	private String  anonymousClientName;
	private boolean cardPasswordAutoAssign;
	private String  cryptoServiceClassName;
	private boolean adminNeedBlocked;
	private boolean needChangePassword;
	private String  settingKey; /// подумать как избавиться
	private List<Charset> passwordCharsets;
	private int     departmentsAllowedLevel;
	private int     departmentAdminsLimit;
	private boolean needKasperskyScript;
	// Признак того, что администратору запрещено устанавливать схему Индивидуальные права
	private boolean denyCustomRights;
	private int mobilePINLength;
	private String mobilePINRegExp;
	/**
	 * Число попыток подбора логина, разрешенное пользователю при регистрации изнутри ЕРИБ.
	 */
	private int numberOfLoginAttemptsAtRegistration;
	private int minuteToResetCaptchaAtRegistration;
	private int smsBankingSessionLifetime;
	private long timeToBlock;

	//Время задержки до отображения капчи (в секундах)
	private long commonCaptchaDelay;
	//Минимальное время задержки до снятия правила постоянного отображения капчи (в секундах)
	private long captchaBlockRulingStoppingDelay;
	//Включена ли капча всегда
	private boolean isTurnOnCaptchaAll;

	public SimpleSecurityConfig(PropertyReader reader, Application app)
	{
		super(reader);
		application = app;
	    ApplicationInfo applicationInfo = new ApplicationInfo(application);
	    switch (application)
	    {
		    case PhizIC:
			    settingKey = "client"; // SecurityService.SCOPE_CLIENT ??
			    break;
		    case atm:
			    settingKey = "client";
			    break;
		    case CSAFront:
			    settingKey = "csa_client_password";
			    break;
		    case socialApi:
		    {
			    settingKey = "client";
			    break;
		    }
		    default:
			    if(applicationInfo.isMobileApi())
			        settingKey = "client"; // SecurityService.SCOPE_CLIENT ??
			    else
			        settingKey = "employee"; // SecurityService.SCOPE_EMPLOYEE ??
			    break;
	    }
    }

	public String getProperty ( String key )
	{
		String value = super.getProperty(key + "." + application);
		if (StringHelper.isEmpty(value))
		{
			 value = super.getProperty(key);
		}
		return value;
	}

	private String getReplaceApplicationProperty(String key)
	{
		Application replaceApp = application;
		// для шедулера, имитирующего действия сотрудника(например, при репликации)
		if(replaceApp == Application.Scheduler)
			replaceApp = Application.PhizIA;

		return getProperty(key + "." + replaceApp);
	}

    public String getPermissionProviderClassName()
    {
        return permissionProvider;
    }

	public String getGuestPermissionProviderClassName()
	{
		return guestPermissionProvider;
	}

	public String getPermissionCalculatorClassName()
	{
		return permissionCalculator;
	}	

	public String getAdminPermissionProvider()
	{
		return adminPermissionProvider;
	}

	public String getLoginInfoProviderClassName()
	{
		return loginInfoProvider;
	}

	public String getDefaultAdminName()
    {
        return superUserName;
    }

	public String getAnonymousClientName()
	{
		return anonymousClientName;
	}

	public int getLoginAttempts ()
    {
        return loginAttempts;
    }

	public int getPasswordLifeTime()
	{
		return passwordLifeTime;
	}

	public int getCardPasswords()
    {
        return cardPasswords;
    }

    public int getBlockedTimeout ()
    {
        return blockedTimeout;
    }

    public int getConfirmAttempts ()
    {
        return confirmAttempts;
    }

    public int getPasswordLength()
    {
        return passwordLength;
    }

    public int getCardsCount()
    {
        return cardsCount;
    }

    public String getPasswordAllowedChars()
    {
        return passwordAllowedChars;
    }

	public String getLoginAllowedChars()
	{
		return loginAllowedChars;
	}

	public int getCardPasswordLength()
    {
        return cardPasswordLength;
    }

    public String getCardPasswordAllowedChars()
    {
        return cardPasswordAllowedChars;
    }

	public boolean isCardPasswordAutoAssign()
	{
		return cardPasswordAutoAssign;
	}

	public String getCryptoServiceClassName()
	{
		return cryptoServiceClassName;
	}

	public int getDepartmentsAllowedLevel()
	{
		return departmentsAllowedLevel;
	}

	public int getMobilePINLength()
	{
		return mobilePINLength;
	}

	public String getMobilePINRegExp()
	{
		return mobilePINRegExp;
	}

	public boolean getNeedLoginConfirmAutoselect()
	{
		return Boolean.parseBoolean(getProperty(Constants.LOGIN_CONFIRM_AUTOSELECT_PREFIX + application));
	}

	public boolean getNeedPaymentConfirmAutoselect()
	{
		return Boolean.parseBoolean(getProperty(Constants.PAYMENT_CONFIRM_AUTOSELECT_PREFIX + application));
	}

	public int getNumberOfLoginAttemptsAtRegistration()
	{
		return numberOfLoginAttemptsAtRegistration;
	}

	public int getMinuteToResetCaptchaAtRegistration()
	{
		return minuteToResetCaptchaAtRegistration;
	}

	public void doRefresh()
    {
	    PasswordValidationConfig passwordValidationConfig = ConfigFactory.getConfig(PasswordValidationConfig.class, application);
		permissionProvider       = getProperty(Constants.PERMISSION_PROVIDER);
	    adminPermissionProvider  = getProperty(Constants.ADMIN_PERMISSION_PROVIDER);
	    guestPermissionProvider  = getProperty(Constants.GUEST_PERMISSION_PROVIDER);
		permissionCalculator     = getProperty(Constants.PERMISSION_CALCULATOR);
		loginInfoProvider        = getProperty(Constants.LOGIN_INFO_PROVIDER);
        superUserName            = getReplaceApplicationProperty(Constants.SUPER_USER_NAME);
        loginAttempts            = passwordValidationConfig.getLoginAttempts();
	    passwordLifeTime         = getIntProperty(Constants.PASSWORD_LIFE_TIME);
        cardPasswords            = getIntProperty(Constants.CARDPASSWORDS_COUNT);
        blockedTimeout           = passwordValidationConfig.getBlockedTimeout();
        confirmAttempts          = getIntProperty(Constants.CONFIRM_ATTEMPTS);
        passwordLength           = passwordValidationConfig.getActualPasswordLength(settingKey);
        cardsCount               = getIntProperty(Constants.CARDS_COUNT);
        passwordAllowedChars     = passwordValidationConfig.getPreferredAllowedCharset(settingKey);
        cardPasswordLength       = getIntProperty(Constants.CARDPASSWORD_LENGTH);
        cardPasswordAllowedChars = getProperty(Constants.CARDPASSWORD_ALLOWED_CHARS);
	    loginAllowedChars        = getProperty(Constants.LOGIN_ALLOWED_CHARS);
	    anonymousClientName      = getProperty(Constants.ANONYMOUS_CLIENT_NAME);
	    cardPasswordAutoAssign = getBoolProperty(Constants.CARDPASSWORDS_AUTO_ASSIGN);
	    cryptoServiceClassName   = getProperty(Constants.CRYPTO_SERVICE);
	    adminNeedBlocked		 = getBoolProperty(Constants.ADMIN_NEED_BLOCK);
	    needChangePassword       = getBoolProperty(Constants.CLIENT_NEED_PASSWORD_CHANGE);
	    passwordCharsets         = passwordValidationConfig.getAllowedCharsets(settingKey);
	    departmentsAllowedLevel  = getIntProperty(Constants.DEPARTMENTS_ALLOWED_LEVEL);
	    departmentAdminsLimit    = getIntProperty(Constants.DEPARTMENT_ADMINS_LIMIT);
	    denyCustomRights         = getBoolProperty(Constants.DENY_CUSTOM_RIGHTS);
	    mobilePINLength          = getIntProperty(Constants.API_PIN_LENGTH);
	    mobilePINRegExp          = getProperty(Constants.API_PIN_REGEXP);
		numberOfLoginAttemptsAtRegistration = getIntProperty(Constants.NUMBER_OF_LOGIN_ATTEMPTS_AT_REGISTRATION_KEY);
	    minuteToResetCaptchaAtRegistration = getIntProperty(Constants.MINUTE_TO_RESET_CAPTCHA_AT_REGISTRATION_KEY);
	    smsBankingSessionLifetime = getIntProperty(Constants.SMS_BANKING_SESSION_LIFETIME);
	    needKasperskyScript      = getBoolProperty(Constants.KASPERSKY_SCRIPT);
	    timeToBlock              = getLongProperty(Constants.TIME_TO_BLOCK_BY_INACTIVITY) * DateHelper.MILLISECONDS_IN_DAY;
	    commonCaptchaDelay       = getLongProperty(COMMON_GUEST_CAPTCHA_DELAY);
	    captchaBlockRulingStoppingDelay = getLongProperty(UNTRUSTED_GUEST_CAPTCHA_DELAY);
	    isTurnOnCaptchaAll = getBoolProperty(CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME, false);
    }

	public boolean isAdminNeedBlocked()
	{
		return adminNeedBlocked;
	}

	/**
	 * Обязательно ли менять пароль при первом входе в систему
	 * @return
	 */
	public boolean getNeedChangePassword()
	{
		return needChangePassword;
	}

	/**
	 * @return наборы символов, из которых может состоять пароль
	 */
	public List<Charset> getPasswordCharsets()
	{
		return passwordCharsets;
	}

	public int getDepartmentAdminsLimit()
	{
		return departmentAdminsLimit;
	}
	public boolean isDenyCustomRights()
	{
		return denyCustomRights;
	}

	public int getSmsBankingSessionLifetime()
	{
		return smsBankingSessionLifetime;
	}

	public boolean getNeedKasperskyScript()
	{
		return needKasperskyScript;
	}

	public long getTimeToBlock()
	{
		return timeToBlock;
	}

	/**
	 * Получение минимального време задержки до снятия правила постоянного отображения капчи (в секундах)
	 * @return Минимальное время задержки до снятия правила постоянного отображения капчи (в секундах)
	 */
	public long getCaptchaBlockRulingStoppingDelay()
	{
		return captchaBlockRulingStoppingDelay;
	}

	/**
	 * Получение времени задержки до отображения капчи (в секундах)
	 * @return Времени задержки до отображения капчи
	 */
	public long getCommonCaptchaDelay()
	{
		return commonCaptchaDelay;
	}

	/**
	 * Проверка, включена ли капча всегда
	 * @return Да, если нужно всегда отображать капчу. Нет в противном случае
	 */
	public boolean isTurnOnCaptchaAll()
	{
		return isTurnOnCaptchaAll;
	}
}
