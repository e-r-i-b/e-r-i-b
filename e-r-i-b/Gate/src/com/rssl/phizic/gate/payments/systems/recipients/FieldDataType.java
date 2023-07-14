package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * Перечисление типов полей.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public enum FieldDataType
{
	/**
	 * Строка
	 */
	string("string"),

	/**
	 * Дата
	 */
	date("date"),

	/**
	 * Число со знаком и десятичной точкой
	 */
	number("number"),

   /**
    * Целое число
    */
   integer("integer"),

	/**
	 * Cумма в рублях или валюте
	 */
	money("money"),

	/**
	 * Поле с фиксированным списком допустимых значений.
	 * для выбора используется ровно 1 значение.
	 */
	list("list"),

	/**
	 * Поле с фиксированным списком допустимых значений.
	 * для выбора используется 0 или более значений.
	 */
	set("set"),

	/**
	 * поле для добавления периодов
	 */
	calendar("calendar"),

	/**
	 * внешняя ссылка
	 */
	link("link"),

	/**
	 * Поле на основе графического представления set'a
	 */
	graphicset("graphicset"),

	/**
	 * Поле для выбора "да-нет"(по сути чекбокс)
	 */
	choice("choice"),

	/**
	 * Поле для задания email
	 */
	email("email");

	private String value;

	FieldDataType(String value)
	{
		this.value = value;
	}

	public static FieldDataType fromValue(String value)
	{
		if (string.value.equalsIgnoreCase(value))
			return string;
		if (date.value.equalsIgnoreCase(value))
			return date;
		if (number.value.equalsIgnoreCase(value))
			return number;
		if (money.value.equalsIgnoreCase(value))
			return money;
		if (integer.value.equalsIgnoreCase(value))
			return integer;
		if (list.value.equalsIgnoreCase(value))
			return list;
		if (set.value.equalsIgnoreCase(value))
			return set;
		if (calendar.value.equalsIgnoreCase(value))
			return calendar;
		if (link.value.equalsIgnoreCase(value))
			return link;
		if (graphicset.value.equalsIgnoreCase(value))
			return graphicset;
		if (choice.value.equalsIgnoreCase(value))
			return choice;
		if (email.value.equalsIgnoreCase(value))
			return email;

		throw new IllegalArgumentException("Неизвестный тип поля [" + value + "]");
	}
}