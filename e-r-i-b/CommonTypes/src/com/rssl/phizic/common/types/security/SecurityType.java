package com.rssl.phizic.common.types.security;

/**
 * @author osminin
 * @ created 09.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������������
 */
public enum SecurityType
{
	HIGHT("�������� ������� ������� ������������."),                                // ������� �������
	MIDDLE("�������� ������� ������� ������������."),                               // ������� �������
	LOW("�������� ������ ������� ������������: ������ ����������.");                // ������ �������

	private String description;

	SecurityType(String description)
	{
		this.description = description;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}
}
