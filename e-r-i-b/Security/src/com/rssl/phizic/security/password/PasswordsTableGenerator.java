package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.Login;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 14:29:30
 * Description: ��������� ��� �������� ������� �������
 */
public interface PasswordsTableGenerator
{

	/**
	 * ����� ������� ������� ������� ��� ����������� ������
	 * @param login - �����
	 * @return ������ �������
	 * @throws Exception
	 */
	public Password[] generatePasswords(Login login) throws Exception;
}
