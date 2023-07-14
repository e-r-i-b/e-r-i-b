package com.rssl.phizic.common.types;

/**
 * ��� ������ ����������
 * @ author gorshkov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public enum FavouriteTypeLink
{
	USER("U", "C����� ��������� �������������"),
	AUTO("A", "������ ��������� �������������");

	private String value;
	private String description;

	FavouriteTypeLink(String value, String description)
	{
		this.value = value;
		this.description = description;
	}

	public String toValue()
	{
		return value;
	}

	public String getDescription()
	{
		return description;
	}

	public static FavouriteTypeLink fromValue(String value)
	{
		if( value.equals(USER.value)) return USER;
		if( value.equals(AUTO.value)) return AUTO;
		throw new IllegalArgumentException("����������� ��� cc���� [" + value + "]");
	}
}
