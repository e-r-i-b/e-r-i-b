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
 * ���������� ������ �����
 */
public interface CurrencyRateUpdateService extends Service
{
	/**
	 *
	 * @param rate - ������ ������ �����
	 * @param office - ������������� �����
	 * @param orderNum - ����� �������
	 * @param orderDate - ���� �������
	 * @param startDate - ����/����� ����� � ��������
	 */
	public void updateRate(List<Pair<CurrencyRate, BigDecimal>> rate, Office office, String orderNum,
		Calendar orderDate, Calendar startDate) throws GateException;

	/**
	 * �������� �� ������������� ������ �� ����������
	 * @param paramsMap - ���� � ����������� ��� �������
	 * @return - true - ����������, false - ���
	 * @throws GateException
	 */
	public boolean rateExistByOrderNumber(Map<String, Object> paramsMap) throws GateException;
}
