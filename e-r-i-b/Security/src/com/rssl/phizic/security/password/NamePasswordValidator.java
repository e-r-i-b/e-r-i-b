package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 15:08:07
 */
public interface NamePasswordValidator
{
    /**
     * ��������� ���������� ��� ������������ � ������
     * @param userId userId ������������
     * @param password ������
     * @return login ��������� �� ���� id
     * @exception SecurityLogicException �������� login\password,
     * ��������� ������� ������ (�������������) etc
     * @exception SecurityException ������ ������ ��� ���������
     */
    CommonLogin validateLoginInfo(String userId, char[] password) throws SecurityLogicException, SecurityException;
}
