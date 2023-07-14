package com.rssl.phizic.gate.einvoicing;

/**
 * ��������� ���������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public enum RecallState
{
	CREATED ("������"),
	ERROR ("������ ����������"),
	EXECUTED ("�������"),
	REFUSED ("�������");

	final String description;

	RecallState(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
