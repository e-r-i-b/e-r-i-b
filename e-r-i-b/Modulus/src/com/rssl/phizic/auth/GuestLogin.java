package com.rssl.phizic.auth;

/**
 * �������� �����
 * @author niculichev
 * @ created 27.12.14
 * @ $Author$
 * @ $Revision$
 */
public interface GuestLogin extends Login
{
	/**
	 * @return ������� ��������������
	 */
	String getAuthPhone();

	/**
	 * @return ���������� ��� �����
	 */
	Long getGuestCode();
}
