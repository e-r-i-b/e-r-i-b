package com.rssl.phizic.gate.commission;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * �������� ������ ��� ��������� ������ ��������
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefCommissionTariffService extends Service
{
	/**
	 * ��������� ������ �� ���� ������ � ���� ��������
	 * @param currencyCode ��� ������
	 * @param transferType ��� ��������
	 * @return ����� ��������
	 * @throws GateException
	 */
	CommissionTariff getTariff(String currencyCode, TransferType transferType) throws GateException;
}
