package com.rssl.phizic.gate.mobilebank;

/**
* @author Erkin
* @ created 10.11.2014
* @ $Author$
* @ $Revision$
*/

/**
 * Код результата обработки связки-на-подключение
 * Задача «Обработка подключений к МБК в ЕРМБ» (миграция на лету)
 */
public enum MBKRegistrationResultCode
{
	/**
	 * успешно зарегистрировано в ЕРМБ
	 */
	SUCCESS(0),

	/**
	 * ошибка при регистрации в ЕРМБ, зарегистрировать в МБК
	 */
	ERROR_REG(1),

	/**
	 * ошибка при регистрации в ЕРМБ, не регистрировать в МБК
	 */
	ERROR_NOT_REG(2),
	;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Код в МБК
	 */
	public final int mbkValue;

	private MBKRegistrationResultCode(int mbkValue)
	{
		this.mbkValue = mbkValue;
	}

	@Override
	public String toString()
	{
		return name() + "(" + mbkValue + ")";
	}
}
