package com.rssl.phizic.authgate.authorization;

import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;
import com.rssl.phizic.authgate.AuthConfig;

/**
 * @author Gainanov
 * @ created 21.07.2009
 * @ $Author$
 * @ $Revision$
 */
public interface AuthGateService
{
	/**
	 * ��������� ��������� �������������� �� ����������� ��������������
	 * @param params ��������� ��� ��������
	 * @return ��������� ���������� �������������� (���������� ��� ��������� ������ ��� ��� � �������������� ��������� such as ��� � �.�.)
	 */
	public AuthParamsContainer checkSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * ���������� �������������� ��������������
	 * @param params �������� ��� ���������� (������������� �������� ������ + ���. ���������)
	 * @return ��������� ���������� (����� ��� �������� �� �������� ���.��������������...)
	 */
	public AuthParamsContainer prepareAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 *  �������� ����������� ���. ��������������
	 * @param params ��������� ��� �������� (����� ���������� �� �������� ��������������...)
	 * @return ��������� ����������� �������� (��� ���������, ��� �������������� � �.�.)
	 */
	public AuthParamsContainer checkAuthentication(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * ���������� ��� �������� ��������� ������ � ��������� �������
	 * @param params ��������� ��� �������� (������������� ������ � �.�.)
	 * @return ����� ��� �������� ��������� ������
	 */
	public AuthParamsContainer moveSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * �������� ������ ������� ������� ����������� (���)
	 * @param params ��������� (������������� ������)
	 * @return ��������� ���������� �������.
	 */
	public AuthParamsContainer closeSession(AuthParamsContainer params) throws AuthGateException, AuthGateLogicException;

	/**
	 * �������� ������ � ���� ��
	 * @param userId ������������ ������������(iPas)
	 * @param password ������
	 * @return ��������� ���������� �������(������ � �����).
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer prepareSession(String userId, String password) throws AuthGateException, AuthGateLogicException;

	/**
	 * @return ������ ���
	 */
	public AuthConfig getConfig();
}
