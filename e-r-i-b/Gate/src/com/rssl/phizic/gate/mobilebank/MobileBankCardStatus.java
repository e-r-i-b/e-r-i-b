package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 20.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус карты в МБ.
 */
public enum MobileBankCardStatus implements Serializable
{
	/**
	 * Активная
	 */
	ACTIVE("A"),

	/**
	 * Приостановлена
	 */
	INACTIVE("I");

	/**
	 * Получение статуса по коду
	 * @param code код статуса в МБ
	 * @return объект-статус
	 */
	public static MobileBankCardStatus forCode(String code)
	{
		if (code == null)
			return null;
		for (MobileBankCardStatus status : values()) {
			if (code.equalsIgnoreCase(status.code))
				return status;
		}
		throw new IllegalArgumentException("Unknown card status' code " + code);
	}

	/**
	 * @return строковый идентификатор статуса
	 */
	public String getCode()
	{
		return code;
	}

	MobileBankCardStatus(String code)
	{
		if (StringHelper.isEmpty(code))
			throw new IllegalArgumentException("Argument 'code' cannot be null");

		this.code = code;
	}

	private final String code;
}