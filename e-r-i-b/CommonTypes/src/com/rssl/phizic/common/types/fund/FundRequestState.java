package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  ������ ������� �� ���� �������
 *  �������, ���������� ��� SYNC ���������� �� ��������� ���������
 */
public enum FundRequestState
{
	OPEN("������"),
	SYNC_OPEN("������"),
	CLOSED("������"),
	SYNC_CLOSED("������");

	private String description;

	private FundRequestState(String description)
	{
		this.description = description;
	}

	/**
	 * @return �������� �������
	 */
	public String getDescription()
	{
		return description;
	}
}
