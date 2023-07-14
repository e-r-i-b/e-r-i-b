package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;

/**
 * User: Evgrafov
 * Date: 26.08.2005
 * Time: 14:56:41
 *
 * ������������ ����������� � ����������, ���������� ��� ���������� ��������������
 */
public interface LoginInfoProvider
{
	/**
	 * ����� �� ������� ������ ������������ ��� ��������� ����� � �������
	 * @param login �����
	 * @return true == ����
	 */
	public boolean needChangePassword (CommonLogin login) throws SecurityDbException;
}
