package com.rssl.phizic.security.password;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 15:12:58
 * ������� ���� ������.
 * ����������� ������ ������������� �������� �������� ������ ������.
 */
public interface PasswordValueGenerator
{
    /**
     * ����� ������� ���� ������
     * @param length       - ����� ������
     * @param allowedChars - ���������� �������, ������� ����� ���� ������������ � ������
     * @return ������ � ���� ������� ��������
     */
    public char [] newPassword(int length, char [] allowedChars);
}
