package com.rssl.phizicgate.rsretailV6r20.money;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizicgate.rsretailV6r20.dictionaries.currencies.CurrencyServiceImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Danilov
 * @ created 03.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyHelper
{
	private CurrencyServiceImpl currencyServiceImpl = new CurrencyServiceImpl(GateSingleton.getFactory());

	//используется в типах хибернейта, поэтому думаем про многопотоковость
	private static Map<Long,Currency> generalCurrencyById = new ConcurrentHashMap<Long,Currency>();
	private static Map<String,Currency> specificCurrencyByCode = new ConcurrentHashMap<String,Currency>();

	/**
	 * Получает валюты из бизнеса(при соотвествующей настройке гейта) по идентификатору в ретейл
	 * @param currencyId идентификатор в ретейл.
	 * @return валюта в назначенном сервисе валют
	 * @throws GateException
	 */
	public Currency getGeneralCurrencyById(Long currencyId) throws GateException, GateLogicException
	{
		Currency result =null;
		if((result = generalCurrencyById.get(currencyId))!=null)
			return result;

		Currency retailCurrency = currencyServiceImpl.findById(currencyId.toString());
		String code = retailCurrency.getCode().equalsIgnoreCase("RUR")?"RUB":retailCurrency.getCode();

		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		result = currencyService.findByAlphabeticCode(code);
		if(result!=null)
			generalCurrencyById.put(currencyId,result);
		return result;
	}
	/**
	 * Получает валюты из шлюза по коду валюты
	 * @param currencyCode буквенный iso код валюты
	 * @return валюта в гейте
	 * @throws GateException
	 */
	public Currency getSpecificCurrencyByCode(String currencyCode) throws GateException
	{
		Currency retailCurrency =null;
		if((retailCurrency = specificCurrencyByCode.get(currencyCode))!=null)
			return retailCurrency;

		retailCurrency = currencyServiceImpl.findByAlphabeticCode(currencyCode);
		if(retailCurrency==null && ( currencyCode.equalsIgnoreCase("RUR")|| currencyCode.equalsIgnoreCase("RUB")) )
		{
			retailCurrency = currencyServiceImpl.findByAlphabeticCode(currencyCode.equalsIgnoreCase("RUR")?"RUB":"RUR");
		}

		if(retailCurrency!=null)
			specificCurrencyByCode.put(currencyCode, retailCurrency);

		return retailCurrency;
	}
}
