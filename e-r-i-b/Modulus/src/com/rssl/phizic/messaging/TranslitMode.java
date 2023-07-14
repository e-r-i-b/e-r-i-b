package com.rssl.phizic.messaging;

/**
 * @author Erkin
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Режим транслитерации сообщений
 */
public enum TranslitMode
{
	/**
	 * Действие по-умолчанию
	 * Обычно означает не транслитерировать
	 * (кроме варианта, когда по-другому нельзя)
	 */
	DEFAULT("0"),

	/**
	 * Транслитерировать
	 */
	TRANSLIT("1");

	private String value;

	TranslitMode(String value)
	{
		this.value = value;
	}

	public String toValue() { return value; }

	public static TranslitMode fromValue(String value)
	{
		if (value.equals(TRANSLIT.value))
			return TRANSLIT;
		if (value.equals(DEFAULT.value))
			return DEFAULT;
		throw new IllegalArgumentException("Неизвестный режим транслитерации [" + value + "]");
	}
}
