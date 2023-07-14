package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.DateSpan;

/**
 * ��������� ������������ ��������� ��������� ������
 * @author Maleyev
 * @ created 08.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface LoanClaimStateUpdateInfo extends StateUpdateInfo
{
	/**
	 * ����� ������������� �������
	 *
	 * @return �����
	 */
	Money getApprovedAmount();
	/**
	 * ���� ������������� �������
	 *
	 * @return ����
	 */
	DateSpan getApprovedDuration();
}
