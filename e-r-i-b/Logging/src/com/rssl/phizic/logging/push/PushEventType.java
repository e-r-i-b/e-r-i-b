package com.rssl.phizic.logging.push;

/**
 * типы Push-уведомлений
 * @author basharin
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushEventType
{
	/**
	 * Уведомление о входе на личную страницу.
	 */
	LOGIN_IN("1"),

	/**
	 * Оповещение из службы помощи
	 */
	HELP("2"),

	/**
	 * Оповещение о приеме на исполнение операций
	 */
	OPERATION("3"),

	/**
	 * Уведомление с одноразовым паролем подтверждения входа
	 */
	LOGIN_CONFIRM("4"),

	/**
	 * Уведомление с одноразовым паролем подтверждения операции, создания шаблонов, автоплатежей и заявок
	 */
	OPERATION_CONFIRM("5"),

	/**
	 * Увеодмление об операции в рамках краудгифтинга (сбора средств)
	 */
	FUND("6");

	PushEventType(String value)
	{
		this.value = value;
	}

	/**
	 * Код статуса
	 */
	private final String value;

	public String toValue()
	{
		return value;
	}

	public static PushEventType fromValue(String value)
	{
		if (value.equals(LOGIN_IN.value))
			return LOGIN_IN;
		if (value.equals(HELP.value))
			return HELP;
		if (value.equals(OPERATION.value))
			return OPERATION;
		if (value.equals(LOGIN_CONFIRM.value))
			return LOGIN_CONFIRM;
		if (value.equals(OPERATION_CONFIRM.value))
			return OPERATION_CONFIRM;
		if (value.equals(FUND.value))
			return FUND;

		throw new IllegalArgumentException("Неизвестный тип push-уведомления [" + value + "]");
	}


}
