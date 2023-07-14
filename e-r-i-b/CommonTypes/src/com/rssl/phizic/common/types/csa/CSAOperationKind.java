package com.rssl.phizic.common.types.csa;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Тип операции в ЦСА, в зависимости от запроса.
 */
public enum CSAOperationKind
{
	//подтверждение операции
	ConfirmOperation("confirmOperationRq"),
	//аутентификация пользователя
	Authentication("authenticationRq"),
	//Регистрация пользователя
	StartUserRegistration("startUserRegistrationRq"),
	FinishUserRegistration("finishUserRegistrationRq"),
	//Самостоятельная регистрация пользователя
	StartUserSelfRegistration("startUserSelfRegistrationRq"),
	FinishUserSelfRegistration("finishUserSelfRegistrationRq"),
	//Восстановление пароля
	StartRestorePassword("startRestorePasswordRq"),
	FinishRestorePassword("finishRestorePasswordRq"),
	//Открытие сессии ЕРИБ
	StartCreateSession("startCreateSessionRq"),
	FinishCreateSession("finishCreateSessionRq"),
	//Генерация пароля
	GeneratePassword("generatePasswordRq"),
	GeneratePassword2("generatePassword2Rq"),

	//Смена пароля
	ChangePassword("changePasswordRq"),
	//Смена логина
	ChangeLogin("changeLoginRq"),
	//Двухшаговое открытие мобильной сессии
	StartCreateMobileSession("startCreateMobileSessionRq"),
	FinishCreateMobileSession("finishCreateMobileSessionRq"),
	//Двухшаговое открытие мобильной сессии
	StartCreateSocialSession("startCreateSocialSessionRq"),
	FinishCreateSocialSession("finishCreateSocialSessionRq"),
	//интеграция с деловой средой
	//начинает процесс подтверждения верификации
	InitializeVerifyBusinessEnvironment("initializeVerifyBusinessEnvironmentRq"),
	//запуск процесса верификации
	VerifyBusinessEnvironment("verifyBusinessEnvironmentRq"),
	//Блокировка профиля пользователя
	LockProfile("lockProfileRq"),
	//Разлокировка профиля пользователя
	UnlockProfile("unlockProfileRq"),
	//Двухшаговое открытие сессии АТМ
	StartCreateATMSession("startCreateATMSessionRq"),
	FinishCreateATMSession("finishCreateATMSessionRq"),
	//Аутентификация ЕРМБ
	FindProfileNodeByPhone("findProfileNodeByPhoneRq"),
	//Отмена регистрации МП
	CancelMobileRegistration("cancelMobileRegistrationRq"),
	//Регистрация МП
	StartMobileRegistration("startMobileRegistrationRq"),
	FinishMobileRegistration("finishMobileRegistrationRq"),
	//Отмена регистрации МП
	CancelSocialRegistration("cancelSocialRegistrationRq"),
	//Регистрация МП
	StartSocialRegistration("startSocialRegistrationRq"),
	FinishSocialRegistration("finishSocialRegistrationRq"),
	//Добавление информации о блокировке пользоваетля в Ериб-е
	LockProfileInfo("lockProfileCHG071536Rq"),
	//Добавление информации о блокировке пользоваетля в Ериб-е
	LockProfileInfoByProfileId("lockProfileCHG071536ByProfileIdRq"),
	//проверка IMSI
	checkIMSI("checkIMSIRq"),
	//подтверждение гостевого входа
	confirmGuestEntry("confirmGuestOperationRq"),
	//завершение гостевого входа;
	finishGuestEntry("finishCreateGuestSessionRq");

	private String requestType;

	CSAOperationKind(String requestType)
	{
		this.requestType = requestType;
	}

	public String getRequestType()
	{
		return requestType;
	}

	public static CSAOperationKind fromValue(String requestType)
	{
		for(CSAOperationKind kind : CSAOperationKind.values())
			if(kind.requestType.equals(requestType))
				return kind;
		throw new IllegalArgumentException("Неизвестный тип операции [" + requestType + "]");
	}
}
