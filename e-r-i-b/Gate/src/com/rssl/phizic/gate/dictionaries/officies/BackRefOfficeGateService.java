package com.rssl.phizic.gate.dictionaries.officies;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * �������� ������ ��������� ������
 * @author niculichev
 * @ created 24.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefOfficeGateService extends Service
{
	/**
	 * ����� ����� �� �������� ��������������
	 * @param id ������� �������������
	 * @return ����
	 * @throws GateException
	 */
	Office getOfficeById(String id) throws GateException, GateLogicException;

	/**
	 * ����� ����� �� ��� ����
	 * @param code ��� �����
	 * @return ����
	 * @throws GateException
	 */
	Office getOfficeByCode(Code code) throws GateException, GateLogicException;
}
