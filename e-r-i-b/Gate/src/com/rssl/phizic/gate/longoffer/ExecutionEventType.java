package com.rssl.phizic.gate.longoffer;

/**
 * @author krenev
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum ExecutionEventType
{
	BY_ANY_RECEIPT("��� ����� ����������"), // ��� ����� ����������
	BY_DEBIT("��� ��������"),// ��� �������
	BY_CAPITAL("��� �������������"), // ��� �������������
	BY_SALARY("��� ���������� ��������"), // ��� ���������� ��������
	BY_PENSION("��� ���������� ������"), // ��� ���������� ������
	BY_PERCENT("��� ���������� ���������"), // ��� ���������� ���������
	ONCE_IN_WEEK("��� � ������"), //��� � ������
	ONCE_IN_MONTH("������ �����"), // ��� � �����
	ONCE_IN_QUARTER("������ �������"), // ��� � �������
	ONCE_IN_HALFYEAR("������ ���������"), // ��� � ���������
	ONCE_IN_YEAR("������ ���"), // ��� � ���
	ON_OVER_DRAFT("��� ���������� �� ����� ����������"), // ��� ���������� �� ����� ����������
	ON_REMAIND("�����, ������� �� ����� �������� ������ ���������� �������"), //  �����,   �������  �� �����
	REDUSE_OF_BALANCE("��� �������� ������� �������� �����"), // ��� �������� ������� �������� ����� �� ��������� �����
	BY_INVOICE("��� ����������� �����");

	private String description;

	ExecutionEventType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * ������������� �� ��� �����������
	 * @param eventType ��� �����������
	 * @return true - ��
	 */
	public static boolean isPeriodic(ExecutionEventType eventType)
	{
		return ExecutionEventType.ONCE_IN_MONTH == eventType || ExecutionEventType.ONCE_IN_QUARTER == eventType
				|| ExecutionEventType.ONCE_IN_HALFYEAR == eventType || ExecutionEventType.ONCE_IN_YEAR == eventType
					|| ExecutionEventType.ONCE_IN_WEEK == eventType;
	}
}
