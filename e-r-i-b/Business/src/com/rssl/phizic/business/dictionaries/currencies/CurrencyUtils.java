package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.business.BusinessException;

/**
 @author Pankin
 @ created 08.02.2011
 @ $Author$
 @ $Revision$
 */
public class CurrencyUtils
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Возвращает валюту по цифровому коду.
	 *
	 * @param numericCode цифровой код.
	 * @return валюту.
	 * @throws GateException
	 */
	public static Currency getCurrency(String numericCode) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency currency = currencyService.findByNumericCode(numericCode);
		if (currency == null)
		{
			log.error("В справочнике не найдена валюта с цифровым кодом " + numericCode);
			return null;
		}
		return currency;
	}

	/**
	 * Утилитный метод получения валюты по ISO коду
	 * @param currencyCode код валюты
	 * @return валюта или null, если не найдено.
	 */
	public static Currency getCurrencyByISOCode(String currencyCode)
	{
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currencyCode);
			if (currency != null)
				return currency;

			log.error("В справочнике не найдена валюта с кодом " + currencyCode);
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении валюты с кодом " + currencyCode, e);
			return null;
		}
		return null;
	}

	/**
	 * Получение буквенного ISO кода валюты по цифровому
	 * @param numericCode цифровой ISO код валюты
	 * @return буквенный ISO код
	 */
	public static String getCurrencyCodeByNumericCode(String numericCode)
	{
		try
		{
			Currency currency = getCurrency(numericCode);
			return currency == null ? "" : currency.getCode();
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении валюты по цифровому коду " + numericCode, e);
			return "";
		}
	}

	/**
	 * Возвращает бизнесовую валюту.
	 *
	 * @param currency валюта для адаптиования.
	 * @return адаптированную валюту.
	 * @throws BusinessException
	 */
	public static Currency adaptCurrency(Currency currency) throws BusinessException
	{
		try
		{
			if (currency instanceof CurrencyImpl) //если валюта бизнесовой реализации, то все хорошо.
				return currency;
			else
			{
				return getCurrency(currency.getNumber());
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
