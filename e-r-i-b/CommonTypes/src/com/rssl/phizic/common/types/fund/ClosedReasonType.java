package com.rssl.phizic.common.types.fund;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 *  ��� ������� �������� ������� �� ���� �������
 */
public enum ClosedReasonType
{
	FUND_COMPLETED("������ �� ������� ����� ������ �����."),
	FUND_TIMEOUT("���� ����� ������� �����."),
	FUND_CLOSED("������ �� ���������� ���������� �������.");

	private String description;

	private ClosedReasonType(String description)
	{
		this.description = description;
	}

	/**
	 * @return �������� ����
	 */
	public String getDescription()
	{
		return description;
	}
}
