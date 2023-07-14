package com.rssl.auth.security;

/**
 * ��������� �������� ����������� �������� ������������
 * @author niculichev
 * @ created 14.11.13
 * @ $Author$
 * @ $Revision$
 */
public interface SecurityManager
{
	/**
	 * ���������� ���������������� ��������
	 * @param key ���� ��� ���������
	 */
	void processUserAction(String key);

	/**
	 * �������� �� �������� ��������� � ������ ��������� ����������
	 * @param key ���� ���������
	 * @return true - �������� �� ����� ����������
	 */
	boolean userTrusted(String key);

	/**
	 * ����� ���������
	 * @param key ���� ���������
	 */
	void reset(String key);
}

