package com.rssl.phizic.messaging.push;

/**
 * Состояния push уведомлений
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushProcStatus
{
	/**
	 * готово к обработке
	 */
	READY("E"),

	/**
	 * зарезервировано на обработку
	 */
	RESERVED("R"),

	/**
	 * обработано
	 */
	SEND("P"),

	/**
	 * ошибка обработки
	 */
	ERROR("F");

	PushProcStatus(String value)
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

	public static PushProcStatus fromValue(String value)
	{
		if (value.equals(READY.value))
			return READY;
		if (value.equals(RESERVED.value))
			return RESERVED;
		if (value.equals(SEND.value))
			return SEND;
		if (value.equals(ERROR.value))
			return ERROR;

		throw new IllegalArgumentException("Неизвестный статус сообщения [" + value + "]");
	}

}
