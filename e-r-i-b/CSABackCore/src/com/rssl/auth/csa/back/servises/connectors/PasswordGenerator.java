package com.rssl.auth.csa.back.servises.connectors;

/**
 * @author krenev
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������
 */
public interface PasswordGenerator
{
	/**
	 * �������������� ������.
	 * @throws Exception
	 */

	public void generatePassword() throws Exception;
}
