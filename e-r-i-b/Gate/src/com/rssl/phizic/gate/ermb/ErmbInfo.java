package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.Response;
import com.rssl.phizic.common.types.ermb.ErmbStatus;

/**
 * ���������� �� ��������� ������ ����
 * @author Puzikov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbInfo extends Response
{
	/**
	 * @return ��������� ������
	 */
	public ErmbStatus getStatus();

	/**
	 * @return �������� ������� �����������
	 */
	public String getActivePhone();
}
