package com.rssl.phizic.auth;

/**
 * @author Egorova
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */
public enum BlockingReasonType
{
	/**
	 * ������������� �������� (��-�� �������� ������� �� ����� �������)
	 * todo: ������������� � ����������� �� �������
	 */
	system,
	/**
	 * ������������� ����������� �� ��� ����������
	 */
	employee,
	/**
	 * ������������� ���� �������� ���������� ������������� ����� ������
	 */
	wrongLogons,
	/**
	 * ������������� ��-�� ���������� ������������
	 */
	longInactivity,
}
