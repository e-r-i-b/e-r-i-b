package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * @author Novikov_A
 * @ created 05.06.2007
 * @ $Author$
 * @ $Revision$
 * @deprecated �� ������������� ������ �����.
 */

@Deprecated
public interface StatusChangerTask
{
	/**
	 * ���������� �������� ��������
	 * @param startDate ������ ������� ����������
	 * @param endDate ���������
	 * @throws GateException
	 */
	void updateStatus(Calendar startDate, Calendar endDate) throws GateException, GateLogicException;
}
