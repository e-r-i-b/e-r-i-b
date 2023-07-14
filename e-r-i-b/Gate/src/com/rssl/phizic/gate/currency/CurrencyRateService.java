package com.rssl.phizic.gate.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ��������� ������ �����
 *
 * @author Kosyakov
 * @ created 17.10.2006
 * @ $Author: egorovaav $
 * @ $Revision: 66619 $
 */
public interface CurrencyRateService extends Service
{
	/**
	 * �������� ������� ����
	 * �������� ��� <from> <type> �� <to> 
	 * @param from ������, �� ������� ������������
	 * @param to ������, � ������� ������������
	 * @param type ��� �����
	 * @param office - ����, �� �������� ���������� �������� ����
	 * @param tarifPlanCodeType - ��� ��������� �����, ��� �������� ��������� ����
	 * @return ���� ���������, ��� ����:
	 *           getFromValue - ����� � ������ from
	 *           getFromCurrency � ���������� from
	 *           getToValue - ����� � ������ to
	 *           getToCurrency � ���������� to
	 *           getFactor �ToValue/FromValue
	 *           getReverseFactor �FromValue/ToValue
	 *           getType �  ���������� ��� type
	 * @throws GateException, GateLogicException
	 */
	CurrencyRate getRate (Currency from, Currency to, CurrencyRateType type, Office office,
	                      String tarifPlanCodeType) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��������� ��� ����� from � ������ from �� ������ to
	 * @param from ����� ������� (����� ��������)
	 * @param to ������ ������� (������ ����������)
	 * @param office - ����, �� �������� ���������� �������� ����.
	 * @param tarifPlanCodeType - ��� ��������� �����, ��� �������� ��������� ����
	 * @return ���� ���������, ��� ����:
	 *           getFromValue - ���������� �����
	 *           getToValue - �����, ������� ����� ���������, �.�. ���������� �����, ���������� �� ���� ��������
	 *           getFactor �ToValue/FromValue
	 *           getReverseFactor �FromValue/ToValue
	 *           getType �  null
	 * @throws GateException, GateLogicException
	 */
	CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException;

	/**
	 * �������� ���� ��������� ������ from �� ����� to � ������ to
	 * @param from ������ ��� ������� (������ ��������)
	 * @param to ����� ������� (����� ����������)
	 * @param office - ����, �� �������� ���������� �������� ����.
	 * @param tarifPlanCodeType - ��� ��������� �����, ��� �������� ��������� ����
	 * @return ���� ���������, ��� ����:
	 *           getFromValue - �����, ������� ����� �������, �.�. ���������� �����, ���������� �� ���� ��������
	 *           getToValue � ���������� �����
	 *           getFactor �ToValue/FromValue
	 *           getReverseFactor �FromValue/ToValue
	 *           getType �  null
	 * @throws GateException, GateLogicException 
	 */
	CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException;
}
