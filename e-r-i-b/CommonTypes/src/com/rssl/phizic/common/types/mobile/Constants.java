package com.rssl.phizic.common.types.mobile;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 *
 * константы для МАПИ
 */
public class Constants
{
	public static final String MGUID_FIELD                  = "mGUID";
	public static final String LOGIN_FIELD                  = "login";
	public static final String PASSWORD_FIELD               = "password";
	public static final String SMS_PASSWORD_FIELD           = "smsPassword";
	public static final String VERSION_FIELD                = "version";
	public static final String IS_LIGHT_SCHEME_FIELD        = "isLightScheme";
	public static final String APP_TYPE_FIELD               = "appType";
	public static final String DEVICE_ID_FIELD              = "devID";
	public static final String CLIENT_ID_FIELD              = "extClientID";
	public static final String CARD_FIELD                   = "card";
	public static final String CAPTCHA_FIELD                = "captcha";
	public static final String MOBILE_SDK_DATA_FIELD        = "mobileSdkData";

	public static final String ERROR_PASSWORD_SYMBOLS       = "Код содержит недопустимые символы.";
	public static final String ERROR_PASSWORD_LENGTH        = "Код должен содержать не менее %d символов.";
	public static final String ERROR_PASSWORD_SEQUENCE      = "Код не может быть последовательным (12345, 23456, 76543).";
	public static final String ERROR_PASSWORD_REPEAT        = "Код не может содержать более трех одинаковых цифр подряд (33334, 55555).";
	public static final String ERROR_SMS_PASSWORD           = "Введите SMS-пароль.";
	public static final String ERROR_DEVICE_ID              = "Отсутствует идентификатор устройства.";
	public static final String ERROR_FOUND_USER             = "Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор.";
}
