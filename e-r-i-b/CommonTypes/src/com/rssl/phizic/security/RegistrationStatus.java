package com.rssl.phizic.security;

/**
 * ������ ����������� ������� � ���.
 *
 * @author bogdanov
 * @ created 29.04.2013
 * @ $Author$
 * @ $Revision$
 */

public enum RegistrationStatus
{
	/**
	 * ������� �� �������� ��������������� �����������.
	 */
	OFF,
	/**
	 * � ������� ���� ����������� � ���. ������: ��� ������ ��� iPas ������, ������� ����� ����������� � ���, �� ����� ��� iPas �������. 
	 */
	EXIST,
	/**
	 * � ������� ��� ����������� � ���, �.�. iPas ������.
	 */
	NOT_EXIST;
}
