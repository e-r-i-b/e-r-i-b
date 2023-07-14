package com.rssl.phizic.monitoring.fraud;

/**
 * ����������� � �� �� ������
 *
 * @author khudyakov
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudAuditedObject
{
	/**
	 * �������� ������������� ���������������� ����-����������
	 * @return ������������� ����������
	 */
	String getClientTransactionId();

	/**
	 * �������� � �������� ������������� ��������������� ����-����������
	 * @param transactionId ������������� ����������
	 */
	void setClientTransactionId(String transactionId);

	/**
	 * ��������� ������� ������ ���������
	 * @param refusingReason ������� ������ ���������
	 */
	void setRefusingReason(String refusingReason);
}
