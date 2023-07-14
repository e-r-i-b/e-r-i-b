package com.rssl.phizic.common.type;

/**
 * User: moshenko
 * Date: 25.03.2013
 * Time: 15:51:20
 *
 */
public enum PersonOldIdentityStatus
{
	ACTIVE("�����������"),
	NOT_ACTIVE("�� ���������"),
	DELETED("�������");

	private String value;

	PersonOldIdentityStatus(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public String getValue()
	{
		return value;
	}
}
