package com.rssl.phizic.business.monitoring;

/**
 * @author mihaylov
 * @ created 25.02.2011
 * @ $Author$
 * @ $Revision$
 */
//��������� �����������
public enum MonitoringReport
{
	TOTAL_AGREEMENT_COUNT("����� ���������� ���������"),

	TOTAL_DISOLV_AGREEMENT_COUNT("����� ���������� ������������ ���������"),

	TODAY_AGREEMENT_COUNT("���������� ����������� ��������� �� ������� ����"),

	TODAY_DISOLV_AGREEMENT_COUNT("���������� ������������ ��������� �� ������� ����"),

	USER_COUNT("���������� �������� ������"),

	EMPLOYEE_COUNT("���������� ����������� ������"),

	PAYMENT_COUNT_TODAY("���������� �������� �� ������� ����"),

	TRANSFER_COUNT_TODAY("���������� ��������� �� ������� ����"),

	LOAN_PAYMENT_COUNT_TODAY("���������� �������� ��������� ������� �� ������� ����"),

	ERROR_DOCUMENT_COUNT_TODAY("���������� ���������� �� ������� ���� � ��������"),

	PAYMENT_COUNT_YESTERDAY("���������� �������� �� ��������� ����"),

	TRANSFER_COUNT_YESTERDAY("���������� ��������� �� ��������� ����"),

	LOAN_PAYMENT_COUNT_YESTERDAY("���������� �������� ��������� ������� �� ��������� ����"),

	ERROR_DOCUMENT_COUNT_YESTERDAY("���������� ���������� �� ��������� ���� � ��������"),

	DISPATCH_DOCUMENT_COUNT("���������� ���������� � ���������"),

	DELAYED_DOCUMENT_COUNT("���������� ���������� � ��������� ��������� ����� 30 �����");
	
	
	private String description;

	MonitoringReport(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
