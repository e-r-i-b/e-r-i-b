package com.rssl.phizic.logging.operations;

/**
 * @author vagin
 * @ created 26.10.2012
 * @ $Author$
 * @ $Revision$
 * Операции в ЦСА для журнала. В журнале будут отражены только те операции, которые описаны в этом енуме!
 */
public enum CSAOperations
{
	AuthenticationOperation("Аутентификация"),
	CancelMobileRegistrationOperation("Отключение (блокировка) мобильного приложения"),
	CheckSessionOperation("Проверка сессии на активность"),
	CloseSessionOperation("Закрытие сессии"),
	MobileAuthenticationOperation("Аутентификация мобильного приложения"),
	MobileRegistrationOperation("Регистрация нового мобильного приложения"),
	RestorePasswordOperation("Восстановление пароля"),
	UserLogon("Вход в систему"),
	UserRegistration("Регистрация нового клиента"),
	GeneratePasswordOperation("Генерация нового пароля"),
	ChangePasswordOperation("Смена пароля"),
	CheckPasswordOperation("Проверка пароля");


	private String description;

	CSAOperations(String str)
	{
		description = str;
	}

	public String getDescription()
	{
		return description;	
	}

	public static String[] getStringValues()
	{
		CSAOperations[] values = values();
		String[] tmp = new String[values.length];
		for (int i = 0; i < values.length; i++)
			tmp[i] = values[i].name();
		return tmp;

	}
}
