package com.rssl.phizic.security.password;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 16:45:35
 *
 * �������� ������
 */
public interface Password
{
    /**
     * @return ���������� true ���� ������ ����������� ����� false
     */
    public boolean isValid();

    /**
     * @return ���������� ������ (��� ������ ��� ��� ���)
     */
    public char[]  getValue();
}
