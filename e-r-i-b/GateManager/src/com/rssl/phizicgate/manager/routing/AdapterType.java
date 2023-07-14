package com.rssl.phizicgate.manager.routing;

/**
 * ��� ���������������� ��������
 * @author komarov
 * @ created 25.10.13 
 * @ $Author$
 * @ $Revision$
 */

public enum AdapterType
{
	NONE("������� ����"), //�� ������������
	ESB("�������������� ����� ����"), //������� �������� �����(������������ � ESB)
	//Integrated GateWay
	IGW("��������������� ����"); //������������ � ���

	private final String description;

	AdapterType(String description)
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
