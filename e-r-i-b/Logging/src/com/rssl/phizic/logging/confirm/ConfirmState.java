package com.rssl.phizic.logging.confirm;

/**
 * Статус запроса на ввод пароля
 * @author lukina
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */

public enum ConfirmState
{
	//Поддержка старых значений(предыдущая информация).
	SUCCESSFUL("успешно"),
	TIMOUT("истечение таймаута"),
	SYSTEM_ERROR("не удалось отправить SMS-пароль"),
	CARD_ERROR("нет доступных чековых паролей"),
	CAP_ERROR("не удалось инициировать подтверждение CAP паролем"),
	CLIENT_ERROR("неправильный ввод пароля"),
	NEW_PASSW("пароль не введен"),


	INIT_SUCCESS("Успешно", "Отправка кода подтверждения по смс"),
	INIT_FAILED("Не удалось отправить SMS-пароль", "Отправка кода подтверждения по смс"),

	CONF_SUCCESS("Успешно", "Ввод смс-кода подтверждения"),
	CONF_FAILED("Неуспешно", "Ввод смс-кода подтверждения"),
	CONF_TIMEOUT("Истечение таймаута", "Ввод смс-кода подтверждения")
	;

	private String description;
	private String operationName;

	ConfirmState(String description)
	{
		this.description = description;
	}

	ConfirmState(String description, String operationName)
	{
		this(description);
		this.operationName = operationName;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return название операции
	 */
	public String getOperationName()
	{
		return operationName;
	}
}
