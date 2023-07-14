package com.rssl.phizic.gate.commission;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author vagin
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 * ������ �������� ������������� ������� ����������� ������ ������� � ��������(��������� � ��� ����������).
 */
public interface BackRefCommissionTBSettingService extends Service
{
	/**
	 * �������������� �� ����������� ��� ������� �������.
	 * @param payment - ������.
	 * @return true/false - �������������/�� �������������
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(GateDocument payment) throws GateException;

	/**
	 * �������������� �� ����������� ��� ������� �������.
	 * @param payment - ������.
	 * @param office - �������������
	 * @return true/false - �������������/�� �������������
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(Office office, GateDocument payment) throws GateException;
}
