package com.rssl.phizic.gate.ips;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;

/**
 * @author Erkin
 * @ created 27.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ��������� ���������� ���������� �� ��������� ��������
 */
public interface CardOperationInfoService extends Service
{
	/**
	 * �������� ��� �������� �� ����
	 * @param operationTypeCodes - ���� ����� ��������
	 * @return ���� ��������
	 */
	GroupResult<Long, CardOperationType> getOperationTypes(Long... operationTypeCodes);
}
