package com.rssl.phizic.auth;

import com.rssl.phizic.security.SecurityDbException;

/**
 * @author Kosyakov
 * @ created 12.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2121 $
 */
public interface LoginGenerator<L extends CommonLogin>
{
	/**
	 * ������� ����� �����
	 *
	 * @return ��������� �����
	 * @throws SecurityDbException      ������ ������ � ��
	 * @throws DublicateUserIdException ����� ���� id ��� ����
	 */
	L generate() throws SecurityDbException, DublicateUserIdException;

}
