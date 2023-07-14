package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 20.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус подключения (регистрации) в МБ
 */
public enum MobileBankRegistrationStatus implements Serializable
{
	/**
	 * Подключение активно
	 */
	ACTIVE("A"),

	/**
	 * Подключение приостановлено
	 */
	INACTIVE("I"),

	/**
	 * Подключение заблокировано
	 */
	BLOCKED("PB"),

	/**
	 * Подключение заблокировано (регистрационная карта украдена)
	 */
	STOLEN("CB");

	/**
	 * Получение статуса по коду
	 * @param code код статуса в МБ
	 * @return объект-статус
	 */
	public static MobileBankRegistrationStatus forCode(String code)
	{
		if (code == null)
			return null;
		for (MobileBankRegistrationStatus status : values()) {
			if (code.equalsIgnoreCase(status.code))
				return status;
		}
		return null;
	}

	/**
	 * @return строковый идентификатор статуса
	 */
	public String getCode()
	{
		return code;
	}

	private MobileBankRegistrationStatus(String code)
	{
		if (StringHelper.isEmpty(code))
			throw new IllegalArgumentException("Аргумент 'code' не может быть пустым");

		this.code = code;
	}

	private final String code;
}
