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
 * ��������� ���������� � �������
 *
 * @author Kidyaev
 * @ created 11.10.2005
 * @ $Author: lepihina $
 * @ $Revision: 81452 $
 */
public interface CurrencyService extends Service
{
	/**
	 * ������ ���� �����
	 *
	 * @return ������
	 * @throws GateException
	 */
	List<Currency> getAll() throws GateException, GateLogicException;

	/**
	 * ���������� ������ �� id.
	 * ���� ������ �� ������� - null
	 *
	 * @param currencyId ������� ID ������
	 * @return ������ ��� null, ���� �� �������
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyIdCacheKeyComposer.class, name = "Currency.findById")
	Currency findById(String currencyId) throws GateException;

	/**
	 * ���������� ������ �� �� Alphabetic ISO ���� (RUB, USD etc)
	 * ���� ������ �� ������� - null
	 *
	 * @param currencyCode Alphabetic ISO ���
	 * @return ������ ��� null, ���� �� �������
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyNumberCacheKeyComposer.class, name = "Currency.findByAlphabeticCode")
	Currency findByAlphabeticCode(String currencyCode) throws GateException;

	/**
	 * ���������� ������ �� �� Numeric ISO ���� (643, 840 etc)
	 * ���� ������ �� ������� - null
	 *
	 * @param currencyCode Numeric ISO ���
	 * @return ������ ��� null, ���� �� �������
	 * @throws GateException
	 */
	@Cachable(keyResolver= CurrencyIsoCodeCacheKeyComposer.class, name = "Currency.findByNumericCode")
	Currency findByNumericCode(String currencyCode) throws GateException;

	/**
	 * ��������� ������������ ������
	 * @return ������ ������������� ������������ ������
	 * @throws GateException
	 */
	@Cachable(keyResolver= NationalCurrencyCacheKeyComposer.class, name = "Currency.nationalCurrency")
	Currency getNationalCurrency() throws GateException;
}