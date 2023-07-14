package com.rssl.phizic.business.finances.targets;

/**
 * ���� ����� ��� �������� ������
 * ��� ��������� �������� ���������� ������������� mAPI, �.�. ��� �������� ����������� � �� ��� ��������� ������ �����, ���������� � ����� ��.
 * @author lepihina
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 */
public enum TargetType
{
	OTHER("������ ����"),
	AUTO("����������"),
	EDUCATION("�����������"),
	RESERVE("���������� ������"),
	RENOVATION("������"),
	VACATION("�����"),
	APPLIANCE("������� �������"),
	FURNITURE("������"),
	BUSINESS("���� ����"),
	ESTATE("������������"),
	HOLIDAYS("��������");

	private final String description;
	
	TargetType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
