package com.rssl.phizic.gate.ima;

/**
 * @ author: Gololobov
 * @ created: 16.08.2012
 * @ $Author$
 * @ $Revision$
 */
public enum IMAOperationType
{
	CS_MONEY("�������� ��������"),
	CS_METAL("������");

	private String description;

	IMAOperationType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
