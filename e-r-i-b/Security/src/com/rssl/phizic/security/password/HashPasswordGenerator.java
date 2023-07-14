package com.rssl.phizic.security.password;

/**
 * @author mihaylov
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * "������" ��������� ������. ����������� ������� ������ � ������� ��������.
 */
public class HashPasswordGenerator implements PasswordValueGenerator
{
	private String password;

	/**
	 * �����������
	 * @param password - ������ ��� �������������� � �������
	 */
	public HashPasswordGenerator(String password)
	{
		this.password = password;
	}

	public char[] newPassword(int length, char[] allowedChars)
	{
		return password.toCharArray();
	}
}
