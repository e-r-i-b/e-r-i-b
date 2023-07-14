package com.rssl.phizic.logging.operations;

/**
 * @author vagin
 * @ created 29.10.2012
 * @ $Author$
 * @ $Revision$
 * ������� �������� � ���
 */
public enum CSAOperationsState
{
	NEW("����� ����������������"),
	CONFIRMED("��������������"),
	EXECUTED("�����������"),
	REFUSED("����������");

	private String description;

	CSAOperationsState(String str)
	{
		description = str;
	}

	public String getDescription()
	{
		return description;
	}
}
