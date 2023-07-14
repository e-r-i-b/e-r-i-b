package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.StringHelper;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тариф обслуживания в МБ
 */
public enum MobileBankTariff implements Serializable
{
	/**
	 * Полный
	 */
	FULL("F"),

	/**
	 * Экономный
	 */
	ECONOM("S");

	/**
	 * Получение тарифа по коду
	 * @param code код тарифа в МБ
	 * @return объект-тариф
	 */
	public static MobileBankTariff forCode(String code)
	{
		if (code == null)
			return null;
		for (MobileBankTariff tariff : values()) {
			if (code.equals(tariff.code))
				return tariff;
		}
		throw new IllegalArgumentException("Unknown tariff' code " + code);
	}

	/**
	 * @return строковый идентификатор тарифа
	 */
	public String getCode()
	{
		return code;
	}

	private MobileBankTariff(String code)
	{
		if (StringHelper.isEmpty(code))
			throw new IllegalArgumentException("Argument 'code' cannot be null");

		this.code = code;
	}

	private Object readResolve() throws ObjectStreamException
	{
		return forCode(code);
	}

	private final String code;
}
