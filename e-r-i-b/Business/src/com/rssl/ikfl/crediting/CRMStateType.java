package com.rssl.ikfl.crediting;

/**
 * ������� ������� � CRM �� ��������� �������
 *
 * @ author: Gololobov
 * @ created: 06.03.15
 * @ $Author$
 * @ $Revision$
 */
public enum CRMStateType
{
	INNER_ERROR("���������� ������_(ErrorCode=44)", 0),
	OK_STATUS("", 1),
	FILL_REFUSE("����� �� ���������� ������", 9);

	private final String description;
	private final Integer code;

	private CRMStateType(String description, Integer code)
	{
		this.description = description;
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public Integer getCode()
	{
		return code;
	}
}
