package com.rssl.phizic.gate.login;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��������� ������
 */

public interface Login
{
	/**
	 * @return �������������
	 */
	public Long getId();

	/**
	 * ������ �����
	 * @param userId �����
	 */
	public void setUserId(String userId);

	/**
	 * @return �������������
	 */
	public String getUserId();

	/**
	 * ������ ������
	 * @param password ������
	 */
	public void setPassword(String password);

	/**
	 * @return ������
	 */
	public String getPassword();

	/**
	 * @return ������ ����������
	 */
	public List<LoginBlock> getBlocks();

	/**
	 * @return ���� ��������� ������������� ������
	 */
	public Calendar getLastSynchronizationDate();
}
