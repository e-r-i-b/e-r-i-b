package com.rssl.phizic.gate.ima;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public enum IMAccountState
{
	opened("������"),
	closed("������"),
	lost_passbook("������� ����������"),
	arrested("�� ���� ������� �����");

    private String description;

	IMAccountState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
