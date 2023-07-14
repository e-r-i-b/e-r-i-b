package com.rssl.phizic.gate.payments;

import java.util.Calendar;

/**
 * @ author: Gololobov
 * @ created: 13.12.2012
 * @ $Author$
 * @ $Revision$
 */
public interface AccountOrIMATransferBase
{
	/**
	 * @return ������������� ��������
	 */
	public String getOperUId();

	/**
	 * �������� �������������� ��������
	 */
	public void setOperUId(String operUid);

	/**
	 * @return ���� �������� ���������
	 */
	public Calendar getOperTime();

	/**
	 * ��������� ���� �������� ���������
	 */
	public void setOperTime(Calendar operTime);
}
