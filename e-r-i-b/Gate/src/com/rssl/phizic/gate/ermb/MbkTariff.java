package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;

/**
 * Тариф подключения в МБК
 * @author Puzikov
 * @ created 22.07.14
 * @ $Author$
 * @ $Revision$
 */

public enum MbkTariff
{
	//1 – Полный тариф; 0 – Экономный тариф
	//"пакет 3" (соответствует экономному тарифу ЕРМБ)
	FULL("full", 1),
	SAVING("saving", 0),
	PACKAGE3("saving", 3),
	;

	private final String stringCode;
	private final int mbkCode;

	MbkTariff(String stringCode, int mbkCode)
	{
		this.stringCode = stringCode;
		this.mbkCode = mbkCode;
	}

	/**
	 * @return кодовое имя тарифа
	 * @see com.rssl.phizic.business.ermb.ErmbTariff#code
	 */
	public String getStringCode()
	{
		return stringCode;
	}

	/**
	 * @return код тарифа в МБК
	 */
	public int getMbkCode()
	{
		return mbkCode;
	}

	/**
	 * @param mbkCode код тарифа в МБК
	 * @return тариф
	 */
	public static MbkTariff fromMbkCode(int mbkCode)
	{
		for (MbkTariff mbkTariff : values())
		{
			if (mbkTariff.getMbkCode() == mbkCode)
				return mbkTariff;
		}

		throw new IllegalArgumentException("Неизвестный код тарифа в МБК: " + mbkCode);
	}

	/**
	 * @param stringCode символьный код тарифа
	 * @see com.rssl.phizic.business.ermb.ErmbTariff#code
	 * @return тариф
	 */
	public static MbkTariff fromStringCode(String stringCode)
	{
		for (MbkTariff mbkTariff : values())
		{
			if (mbkTariff.getStringCode().equals(stringCode))
				return mbkTariff;
		}

		throw new IllegalArgumentException("Неизвестный символьный код тарифа: " + stringCode);
	}
}
