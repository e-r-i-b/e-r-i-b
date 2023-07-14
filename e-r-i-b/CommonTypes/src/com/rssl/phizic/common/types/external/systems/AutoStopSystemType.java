package com.rssl.phizic.common.types.external.systems;

/**
 * ��� ������� �������, ������� ����� ���� �������������� � �������������� ������
 * @author Pankin
 * @ created 22.11.2012
 * @ $Author$
 * @ $Revision$
 */

public enum AutoStopSystemType
{
	CARD("������� ������� ��� ��������� ����"),
	COD("������� ������� ��� ��������� ������"),
	LOAN("������� ������� ��� ��������� ������"),
	ESB("���"),
	MBK("���");

	private String description;

	AutoStopSystemType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
