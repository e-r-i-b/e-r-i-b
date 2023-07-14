package com.rssl.phizic.gate.employee;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 14.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ���������� �����������
 */

public interface EmployeeListFilterParameters
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
	 * @return ����� ������� ����������
	 */
	public String getSeekerLogin();

	/**
	 * @return ������� ����������� ������ �� ���� ��������������
	 */
    public boolean isSeekerAllDepartments();

	/**
	 * @return ������������� �������� ����������
	 */
    public Long getSoughtId();

	/**
	 * @return ����� �������� ����������
	 */
    public String getSoughtLogin();

	/**
	 * @return ��� �������� ����������
	 */
    public String getSoughtFIO();

	/**
	 * @return ������� ����������������� ������� �����������
	 */
    public long getSoughtBlockedState();

	/**
	 * @return ���� ��������� ���������� ������� �����������
	 */
    public Calendar getSoughtBlockedUntil();

	/**
	 * @return ���. ���������� ������� �����������
	 */
    public String getSoughtInfo();

	/**
	 * @return ��� �������� ������� �����������
	 */
    public String getSoughtTB();

	/**
	 * @return ��� ��� ������� �����������
	 */
    public String getSoughtBranchCode();

	/**
	 * @return ��� ��� ������� �����������
	 */
	public String getSoughtDepartmentCode();
}
