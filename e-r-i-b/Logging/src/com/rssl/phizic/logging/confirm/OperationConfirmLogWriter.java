package com.rssl.phizic.logging.confirm;

import java.io.Serializable;

/**
 * @author lukina
 * @ created 09.02.2012
 * @ $Author$
 * @ $Revision$
 */

public interface OperationConfirmLogWriter extends Serializable
{
	/**
	 * ������������� ���� �������� ������������ ��������� ������������� �� ���
	 * @param recipient ����������(�)
	 * @param textToLog ����� ���
	 * @param additionalCheck ������� �������� ����
	 */
	void initializeSMSSuccess(String recipient, String textToLog, boolean additionalCheck);

	/**
	 * ������������� ���� ���������� ������������ ��������� ������������� �� ���
	 * @param recipient ����������(�)
	 * @param textToLog ����� ���
	 * @param additionalCheck ������� �������� ����
	 */
	void initializeSMSFailed(String recipient, String textToLog, boolean additionalCheck);

	/**
	 * ������������� ���� �������� ������������ ��������� ������������� ����� PUSH
	 * @param recipient ����������(�)
	 * @param textToLog ����� ���������
	 */
	void initializePUSHSuccess(String recipient, String textToLog);

	/**
	 * ������������� ���� ���������� ������������ ��������� ������������� ����� PUSH
	 * @param recipient ����������(�)
	 * @param textToLog ����� ���������
	 */
	void initializePUSHFailed(String recipient, String textToLog);

	/**
	 * ������������� ���� �������� ������������ ��������� ������������� ����� ������� ������
	 * @param cardNumber ����� �������� ������
	 * @param passwordNumber ����� ������ � ����
	 */
	void initializeCardSuccess(String cardNumber, String passwordNumber);

	/**
	 * ������������� ���� ���������� ������������ ��������� ������������� ����� ������� ������
	 * @param userId ����� Ipas, ��� �������� ���������� ������� ������
	 */
	void initializeCardFailed(String userId);

	/**
	 * ������������� ���� �������� ������������ ��������� ������������� ����� ���
	 * @param cardNumber ����� �����
	 */
	void initializeCAPSuccess(String cardNumber);

	/**
	 * ������������� ���� ���������� ������������ ��������� ������������� ����� ������� ������
	 */
	void initializeCAPFailed();

	/**
	 * ������������� ���� �������� ������� �������������
	 * @param confirmType ��� ��������� �������������(SMS/PUSH/CARD/CAP)
	 * @param confirmCode ��������� �������� ���
	 */
	void confirmSuccess(ConfirmType confirmType, String confirmCode);

	/**
	 * ������������� ���� ���������� ������� �������������
	 * @param confirmType ��� ��������� �������������(SMS/PUSH/CARD/CAP)
	 * @param confirmCode ��������� �������� ���
	 */
	void confirmFailed(ConfirmType confirmType, String confirmCode);

	/**
	 * ������������� ���� ���������� ������� ������������� �� ������� "����������" ���� �������������
	 * @param confirmType ��� ��������� �������������(SMS/PUSH/CARD/CAP)
	 * @param confirmCode ��������� �������� ���
	 */
	void confirmTimeout(ConfirmType confirmType, String confirmCode);
}