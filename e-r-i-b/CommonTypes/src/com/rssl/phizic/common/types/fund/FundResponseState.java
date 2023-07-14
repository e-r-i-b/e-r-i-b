package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  ������ ��������� ������� �� ���� �������.
 *  �������, ���������� ��� SYNC ���������� �� ��������� ���������
 */
public enum FundResponseState
{
	NOT_READ("�� ��������", ""),    //������ �� ���������, �� ������������ ��������
	READ("��������", "SetReadStateFundOperation"),
	REJECT("��������", "SetRejectStateFundOperation"),
	SUCCESS("������������", "SetSuccessStateFundOperation");

	private String description;
	private String operationKey;

	private FundResponseState(String description, String operationKey)
	{
		this.description = description;
		this.operationKey = operationKey;
	}

	/**
	 * @return �������� �������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return ���� ��������, ������� ������ ����������� ��� �������� � ������
	 */
	public String getOperationKey()
	{
		return operationKey;
	}
}
