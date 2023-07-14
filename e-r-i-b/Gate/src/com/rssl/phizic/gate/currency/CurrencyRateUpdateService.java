package com.rssl.phizic.gate.currency;

import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author gulov
 * @ created 21.09.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Обновление курсов валют
 */
public interface CurrencyRateUpdateService extends Service
{
	/**
	 *
	 * @param rate - список курсов валют
	 * @param office - подразделение банка
	 * @param orderNum - номер приказа
	 * @param orderDate - дата приказа
	 * @param startDate - Дата/время ввода в действие
	 */
	public void updateRate(List<Pair<CurrencyRate, BigDecimal>> rate, Office office, String orderNum,
		Calendar orderDate, Calendar startDate) throws GateException;

	/**
	 * Проверка на существование курсов по параметрам
	 * @param paramsMap - мапа с параметрами для запроса
	 * @return - true - существуют, false - нет
	 * @throws GateException
	 */
	public boolean rateExistByOrderNumber(Map<String, Object> paramsMap) throws GateException;
}
