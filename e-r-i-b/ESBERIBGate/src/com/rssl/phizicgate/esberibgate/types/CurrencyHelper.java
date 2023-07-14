package com.rssl.phizicgate.esberibgate.types;

import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author egorova
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyHelper
{
	/**
	 * Обёртка для того, чтобы в нашей системе работать только с RUR
	 * Получить ISO-код из ретейла по ид валюты
	 * @return ISO-код  валюты
	 */
	public static String getCurrencyCode(String currencyCode) throws GateLogicException
	{
		if (currencyCode == null)
			throw new GateLogicException("Не получен код валюты");
		if(currencyCode.equals("RUR"))
			return "RUB";
		return currencyCode;
	}
}