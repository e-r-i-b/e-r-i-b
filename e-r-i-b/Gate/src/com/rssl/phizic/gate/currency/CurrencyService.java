package com.rssl.phizic.gate.currency;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.CurrencyIdCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.CurrencyIsoCodeCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.CurrencyNumberCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.NationalCurrencyCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * Получение информации о валютах
 *
 * @author Kidyaev
 * @ created 11.10.2005
 * @ $Author: lepihina $
 * @ $Revision: 81452 $
 */
public interface CurrencyService extends Service
{
	/**
	 * Список всех валют
	 *
	 * @return список
	 * @throws GateException
	 */
	List<Currency> getAll() throws GateException, GateLogicException;

	/**
	 * Возвращает валюту по id.
	 * Если валюта не найдена - null
	 *
	 * @param currencyId внешний ID валюты
	 * @return валюта или null, если не найдена
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyIdCacheKeyComposer.class, name = "Currency.findById")
	Currency findById(String currencyId) throws GateException;

	/**
	 * Возвращает вылюту по ее Alphabetic ISO коду (RUB, USD etc)
	 * Если валюта не найдена - null
	 *
	 * @param currencyCode Alphabetic ISO код
	 * @return валюта или null, если не найдена
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyNumberCacheKeyComposer.class, name = "Currency.findByAlphabeticCode")
	Currency findByAlphabeticCode(String currencyCode) throws GateException;

	/**
	 * Возвращает вылюту по ее Numeric ISO коду (643, 840 etc)
	 * Если валюта не найдена - null
	 *
	 * @param currencyCode Numeric ISO код
	 * @return валюта или null, если не найдена
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyIsoCodeCacheKeyComposer.class, name = "Currency.findByNumericCode")
	Currency findByNumericCode(String currencyCode) throws GateException;

	/**
	 * Получение национальной валюты
	 * @return Объект представлющий начинальныую валюту
	 * @throws GateException
	 */
	@Cachable(keyResolver= NationalCurrencyCacheKeyComposer.class, name = "Currency.nationalCurrency")
	Currency getNationalCurrency() throws GateException;
}