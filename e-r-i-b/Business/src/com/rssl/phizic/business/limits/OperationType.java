package com.rssl.phizic.business.limits;

/**
 * @author basharin
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 * ��� �������� ��� ���������� ������
 */

public enum OperationType
{
	IMPOSSIBLE_PERFORM_OPERATION("���������� ��������� ��������"),              //���������� ��������� ��������
	NEED_ADDITIONAL_CONFIRN("������������ � ����"),                             //��������� �������������� �������������
	READ_SIM("��������� �������� �� ����� SIM-�����");                          //���������� sim-�����

	private String description;

	OperationType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
