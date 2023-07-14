package com.rssl.phizic.gate.employee;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������
 */

public interface ContactCenterEmployeeFilterParameters
{
	/**
	 * @return ������ ������ ������
	 */
	public int getFirstResult();

	/**
	 * @return ���������� �������
	 */
	public int getMaxResults();

	/**
	 * @return ���� ��������� ���������� ������� �����������
	 */
	public Calendar getSoughtBlockedUntil();

	/**
	 * @return ������������� �������� ����������
	 */
	public Long getSoughtId();

	/**
	 * @return ��� �������� ����������
	 */
	public String getSoughtFIO();

	/**
	 * @return �������� �������� ����������
	 */
	public String getSoughtArea();
}
