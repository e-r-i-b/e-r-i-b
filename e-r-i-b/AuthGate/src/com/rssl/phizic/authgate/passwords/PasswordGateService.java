package com.rssl.phizic.authgate.passwords;

import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.authgate.AuthGateException;
import com.rssl.phizic.authgate.AuthGateLogicException;

/**
 * @author Gainanov
 * @ created 13.04.2010
 * @ $Author$
 * @ $Revision$
 */
public interface PasswordGateService
{
	/**
	 * ���������� ����� ������������ ������ � ����.
	 * @param container �������� � ���� ��������� ��� ���������� (userId, �����, etc.)
	 * @return ���������. � ������ Way4 iPAS, � ��� ���������� ����� ���� � ����� ������, ���������� ���������  �������
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer prepareOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * �������� ������������ ����� ������������ ������ � ����.
	 * @param container �������� � ���� ��������� ��� �������� (������, etc.)
	 * @return ��������� �������� ������ � ���. ��������� ���������� (���������� ���������� ������� � �.�.)  
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer verifyOTP(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * ������ �� ��������� ������ ������������ ������
	 * @param container ��������� ��� ��������� ������
	 * @return ��������� ��������� (���������, ����� ������, etc.)
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer generateStaticPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * ����� �������� ������� ����������� ���-������
	 * @param container ��������� ������� ������ (�������� ����� �����, ������, etc.)
	 * @return ��������� ������� ������
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer sendSmsPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;


	/**
	 * �������������� �� ������������ ������
	 * @param container ��������� ������� ������ (�����, ������, etc.)
	 * @return ��������� �������� ������ � ���. ��������� ����������
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public AuthParamsContainer verifyPassword(AuthParamsContainer container) throws AuthGateException, AuthGateLogicException;

	/**
	 * �������������� � ������� CAP
	 * @param cardNumber ����� �����
	 * @param capToken   CAP ������
	 * @throws AuthGateException
	 * @throws AuthGateLogicException
	 */
	public void verifyCAP(String cardNumber, String capToken) throws AuthGateException, AuthGateLogicException;
}
