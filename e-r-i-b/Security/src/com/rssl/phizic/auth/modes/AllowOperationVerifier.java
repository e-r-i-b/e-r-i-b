package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;

/**
 * User: Moshenko
 * Date: 01.06.12
 * Time: 14:17
 * ������� �� ���������� ���������(allow-operation), � client-authentication-modes
 */
public interface AllowOperationVerifier extends Serializable
{
	/**
	 *
	 * @param operationClassName ��� ������ ��������.
	 * @param login
	 * @return ��������� �� ��������
	 */
	boolean isAllow(String operationClassName, CommonLogin login);
}
