package com.rssl.phizic.gate.bankroll;

/**
 * ���������� �� �����
 */
public interface AccountTransaction extends TransactionBase
{
	/**
	 * ���������� �������� ��������������
	 * Domain: Text
	 *
	 * @return �������� �������������� ��� null, ���� ������������� �����������
	 */
	String getCounteragent();

	/**
	 * ���������� ����� ����� ��������������
	 * Domain: AccountNumber
	 *
	 * @return ����� ����� �������������� ��� null, ���� ������������� �����������
	 */
	String getCounteragentAccount();

	/**
	 * ���������� ���� ��������������, ��������� �������� (��������, ������������ + ���)
	 * Domain: Text
	 *
	 * @return ��� ��� null, ���� ������������� �����������
	 */
	String getCounteragentBank();

	/**
	 * ���������� �������� ����� ��������������
	 *
	 * @return �������� ����� ��������������
	 */
	String getCounteragentBankName();

	/**
	 * ���������� ���� ����������
	 * 
	 * @return ���� ����������
	 */
	String getBookAccount();

	/**
	 * ���������� ������� ����� ����������
	 *
	 * @return ������� ����� ����������
	 */
	String getCounteragentCorAccount();

	/**
	 * ���������� ���� ��������
	 *
	 * @return ���� ��������
	 */
	String getOperationCode();

	/**
	 * ���������� ����� ���������, ��������������� ��������
	 *
	 * @return ����� ���������, ��������������� ��������
	 */
	String getDocumentNumber();

	/**
	 * @return �������������� ����������� �� �����
	 */
	CardUseData getCardUseData();
}
