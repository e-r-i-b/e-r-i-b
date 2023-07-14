package com.rssl.common.forms.doc;

/**
 * Способ создания документа
 @author Pankin
 @ created 02.12.2010
 @ $Author$
 @ $Revision$
 */
public enum CreationSourceType
{
	// Обычный, клиент сам заполнял поля
	ordinary("0"),

	// Документ создан по шаблону
	template("1"),

	// Документ создан по шаблону мобильного банка
	mobiletemplate("2"),

	// документ создан по копии
	copy("3"),

	// документ создан автоматически (при получении запроса от ВС)
	system("4");

	private String value;

	CreationSourceType(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static CreationSourceType fromValue(String value)
	{
		if (value.equals(ordinary.value))
			return ordinary;
		if (value.equals(template.value))
			return template;
		if (value.equals(mobiletemplate.value))
			return mobiletemplate;
		if (value.equals(copy.value))
			return copy;
		if (value.equals(system.value))
			return system;
		throw new IllegalArgumentException("Неизвестный тип создания документа [" + value + "]");
	}
}
