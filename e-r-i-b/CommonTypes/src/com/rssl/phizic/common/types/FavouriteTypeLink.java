package com.rssl.phizic.common.types;

/**
 * Тип ссылки избранного
 * @ author gorshkov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 */
public enum FavouriteTypeLink
{
	USER("U", "Cсылка добавлена пользователем"),
	AUTO("A", "Ссылка добавлена автоматически");

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
		throw new IllegalArgumentException("Неизвестный тип ccылки [" + value + "]");
	}
}
