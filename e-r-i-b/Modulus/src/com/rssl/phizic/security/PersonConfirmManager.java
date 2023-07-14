package com.rssl.phizic.security;

/**
 * @author Erkin
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ �������� �������������
 */
public interface PersonConfirmManager
{
	/**
	 * ��������� ������������� ��� ��������� ������
	 * @param confirmableTask - ������, ������� ����� �����������
	 */
	void askForConfirm(ConfirmableTask confirmableTask);

	/**
	 * ��������� ������������� ��� ��������� ������
	 * @param confirmableTask - ������, ������� ����� �����������
	 * @param phoneNumber - ����� �������� ��� �������������
	 */
	void askForConfirm(ConfirmableTask confirmableTask, String phoneNumber);

	/**
	 * ����� � ������������� ����� �������������
	 * @param confirmCode - ��� �������������
	 * @param phone �������, �� ������� ��������� ��� �������������
	 * @param primary true - �������� ��� (����� ����), false - �������������� (������ ����� ���)
	 * @return ����� ������������� ��� null, ���� ����� �� ������
	 */
	ConfirmToken captureConfirm(String confirmCode, String phone, boolean primary);

	/**
	 * ���������, ���������� �� ��� �������������, ������� "�����" �� ���������� (���������� �� ����������� ����������� ������ �������)
	 * @param confirmCode - ��� �������������
	 * @param phone �������, �� ������� ��������� ��� �������������
	 * @return
	 */
	boolean similarConfirmCodeExists(String confirmCode, String phone);

	/**
	 * ������ ������������� �� ���������� ������
	 * @param confirmToken - ����� ������������� (never null)
	 */
	void grantConfirm(ConfirmToken confirmToken);
}
