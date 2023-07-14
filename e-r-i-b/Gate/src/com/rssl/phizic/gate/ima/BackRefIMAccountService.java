package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;

/**
 * �������� ������ ��� ������ � ���
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public interface BackRefIMAccountService extends Service
{
	/**
	 * ����� �������� �������������� ��� �� ������
	 * @param loginId ������������� ������ ��������� ���
	 * @param imAccountNumber ����� ���
	 * @return ��������� ��������� �� ��������������� ��� <����� ���, �������������>
	 */
	GroupResult<String, String> findIMAccountExternalId(Long loginId, String... imAccountNumber);
}
