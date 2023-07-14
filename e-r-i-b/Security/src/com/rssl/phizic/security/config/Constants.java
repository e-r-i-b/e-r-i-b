package com.rssl.phizic.security.config;

/**
 * @author Roshka
 * @ created 07.02.2006
 * @ $Author$
 * @ $Revision$
 */
final public class Constants
{
    ///properties' names
	public static final String PERMISSION_PROVIDER             = "com.rssl.iccs.permission-provider";
	public static final String ADMIN_PERMISSION_PROVIDER       = "com.rssl.iccs.permission-provider.admin";
	public static final String GUEST_PERMISSION_PROVIDER       = "com.rssl.iccs.permission-provider.guest";
	public static final String PERMISSION_CALCULATOR           = "com.rssl.iccs.permission-calculator";
	public static final String LOGIN_INFO_PROVIDER             = "com.rssl.iccs.login-info-provider";
	public static final String SUPER_USER_NAME                 = "com.rssl.iccs.super-user";
	public static final String ANONYMOUS_CLIENT_NAME           = "com.rssl.iccs.anonymous-user";

	public static final String PASSWORD_LIFE_TIME              = "com.rssl.iccs.password.lifetime";
	public static final String LOGIN_ATTEMPTS                  = "com.rssl.iccs.login.attempts";
	public static final String BLOCKED_TIMEOUT                 = "com.rssl.iccs.login.blockedTimeout";
	public static final String CONFIRM_ATTEMPTS                = "com.rssl.iccs.confirmAttempts";
	public static final String ADMIN_NEED_BLOCK                = "com.rssl.iccs.admin.needBlock";

	public static final String PASSWORD_LENGTH                 = "com.rssl.iccs.password-generator.password.length";
	public static final String PASSWORD_ALLOWED_CHARS          = "com.rssl.iccs.password-generator.allowed.chars";

	public static final String LOGIN_ALLOWED_CHARS             = "com.rssl.iccs.login-generator.allowed.chars";
	public static final String CLIENT_NEED_PASSWORD_CHANGE     = "com.rssl.iccs.login.client.need.password.change";

	public static final String CARDPASSWORD_LENGTH             = "com.rssl.iccs.card-password-generator.password.length";
	public static final String CARDPASSWORD_ALLOWED_CHARS      = "com.rssl.iccs.card-password-generator.allowed.chars";

	public static final String CARDS_COUNT                     = "com.rssl.iccs.cards.generator.count";
	public static final String CARDPASSWORDS_COUNT             = "com.rssl.iccs.cardpasswords.generator.count";
	public static final String CARDPASSWORDS_AUTO_ASSIGN       = "com.rssl.iccs.cardpasswords.auto.assign";

	//default system settings
	public static final String NUMBER_OF_LOGIN_ATTEMPTS_AT_REGISTRATION_KEY = "com.rssl.iccs.registration.login.attemptnumber";
	public static final String MINUTE_TO_RESET_CAPTCHA_AT_REGISTRATION_KEY = "com.rssl.iccs.registration.login.minute.to.reset.captcha";

	//default schemes
	public static final String DEFAULT_SCHEME                  = "com.rssl.iccs.default.scheme.";
	public static final String BUILDIN_ADMIN_SCHEME            = "com.rssl.iccs.builin.admin.scheme";
	public static final String ANONYMOUS_CLIENT_SCHEME         = "com.rssl.iccs.anonymous.client.scheme";

	//default operation keys
	public static final String USER_LOGON_OPERATION_KEY        = "USER_LOGON_OPERATION_KEY";
	public static final String LOGIN_DEFAULT_OPERATION_KEY     = "LOGIN_DEFAULT_OPERATION_KEY";
	public static final String LOGOFF_DEFAULT_OPERATION_KEY    = "LOGOFF_DEFAULT_OPERATION_KEY";
	public static final String COUNT_ACTIVE_SESSION            = "COUNT_ACTIVE_SESSION";
	public static final String USER_AUTENTICATION_KEY          = "USER_AUTENTICATION_KEY";

	public static final String SMS_BANKING_SESSION_LIFETIME    = "com.rssl.iccs.smsBanking.session.lifeTime";

	public static final String CRYPTO_SERVICE                  = "com.rssl.iccs.crypto-service";

	//default admin departments settings
	public static final String DEPARTMENTS_ALLOWED_LEVEL       = "com.rssl.iccs.departments.allowed.level";
	public static final String DEPARTMENT_ADMINS_LIMIT         = "com.rssl.iccs.departments.admins.limit";
	public static final String DENY_CUSTOM_RIGHTS              = "com.rssl.iccs.deny.custom.rights";

	public static final String MOBILE_PROVIDER_ID_KEY = "mobileProviderProperties_Id_";
	public static final String MOBILE_PROVIDER_CODE_KEY = "mobileProviderProperties_providerCode_";
	public static final String MOBILE_SERVICE_CODE_KEY = "mobileProviderProperties_serviceCode_";
	public static final String MOBILE_PROVIDER_KEY = "com.rssl.business.mobileProvidersProperties_";

	public static final String OLD_DOC_ADAPTER_ID_KEY = "oldDocAdapterProperties_Id_";
	public static final String OLD_DOC_ADAPTER_CODE_KEY = "oldDocAdapterProperties_adapterCode_";
	public static final String OLD_DOC_ADAPTER_KEY = "com.rssl.business.oldDocAdaptersProperties_";

	//default deprtments settings
	public static final String DEPARTMENT_OSB_LEVEL            = "com.rssl.iccs.departments.osb.level";

	public static final String CLAIM_WORKING_LIFE              = "com.rssl.iccs.claim.working.life";

	public static final String CONFIRM_PUSH_PASSWORD_FIELD = "$$confirmPushPassword";
	public static final String CONFIRM_SMS_PASSWORD_FIELD = "$$confirmSmsPassword";
	public static final String CONFIRM_CARD_PASSWORD_FIELD = "$$confirmCardPassword";
	public static final String CONFIRM_CAP_PASSWORD_FIELD = "$$confirmCapPassword";
	public static final String CONFIRM_PLASTIC_CARD_NUMBER_FIELD      = "$$confirmPlasticCardNumber";
	public static final String CONFIRM_PLASTIC_CARD_TYPE_FIELD        = "$$confirmPlasticCardType";
	public static final String CONFIRM_PLASTIC_TRANSACTION_DATE_FIELD = "$$confirmPlasticTransactionDate";
	public static final String CONFIRM_PLASTIC_TRANSACTION_TIME_FIELD = "$$confirmPlasticTransactionTime";
	public static final String CONFIRM_PLASTIC_TERMINAL_NUMBER_FIELD  = "$$confirmPlasticTerminalNumber";
	public static final String CONFIRM_PLASTIC_FORM_NAME_FIELD        = "$$formName";
	public static final String CONFIRM_PLASTIC_EMPLOYEE_LOGIN_FIELD   = "$$employeeLogin";

	//Текущие параметры смс-пароля
	public static final String SMS_CURRENT_TIME_TO_LIVE = "smsCurrentTimeToLive";
	public static final String SMS_ATTEMPTS_LEFT        = "smsAttemptsLeft";

	//mobileAPI
	public static final String API_PIN_LENGTH                =  "mobile.api.pin.length";
	public static final String API_PIN_REGEXP                =  "mobile.api.pin.regexp";

	//MB
	public static final String MB_REGISTRATION_REPEAT_INTERVAL    =	"mb.registration.repeat.interval";

	public static final String LOGIN_CONFIRM_AUTOSELECT_PREFIX = "login.confirm.autoselect.";
	public static final String PAYMENT_CONFIRM_AUTOSELECT_PREFIX = "payment.confirm.autoselect.";

	public static final String KASPERSKY_SCRIPT = "com.rssl.iccs.kaspersky.script";
	public static final String TIME_TO_BLOCK_BY_INACTIVITY      = "com.rssl.iccs.employeeSettings.timeToBlockAccountByInactivity";
	public static final String BLOCK_BY_INACTIVITY_REASON_DESCR = "Блокировка по длительной неактивности";
}
