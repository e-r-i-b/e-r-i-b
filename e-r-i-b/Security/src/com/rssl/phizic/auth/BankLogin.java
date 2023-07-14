package com.rssl.phizic.auth;

import java.util.Calendar;

/**
 * @author Roshka
 * @ created 27.12.2005
 * @ $Author$
 * @ $Revision$
 */
public interface BankLogin extends CommonLogin
{
	/**
	 * ������ ���� ��������� ������������� ������
	 * @param lastSynchronizationDate ���� ��������� ������������� ������
	 */
	public void setLastSynchronizationDate(Calendar lastSynchronizationDate);

	/**
	 * @return ���� ��������� ������������� ������
	 */
	public Calendar getLastSynchronizationDate();

}
