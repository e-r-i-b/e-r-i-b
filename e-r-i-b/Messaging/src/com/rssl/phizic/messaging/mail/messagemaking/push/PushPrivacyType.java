package com.rssl.phizic.messaging.mail.messagemaking.push;

/**
 * Тип приватности push-сообщения
 * @author basharin
 * @ created 08.10.13
 * @ $Author$
 * @ $Revision$
 */

public enum PushPrivacyType
{
	/**
	 * Публичное
	 */
	PUBLIC("Y"),

	/**
	 * Закрытое
	 */
	PRIVATE("N");

	PushPrivacyType(String value)
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
}

