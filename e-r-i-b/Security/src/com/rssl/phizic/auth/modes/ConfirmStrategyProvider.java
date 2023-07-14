package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.UserPrincipal;

/**
 * User: Moshenko
 * Date: 22.05.12
 * Time: 12:06
 * ��������� ���������� ��������� �������������
 */
public interface ConfirmStrategyProvider
{
	/**
	 * ����� ��� ��������� ��������� ��� ��������� ������������
	 * @return ��������� �������������
	 */
	ConfirmStrategy getStrategy();

	/**
	 * @return UserPrincipal
	 */
	UserPrincipal getPrincipal();
}
