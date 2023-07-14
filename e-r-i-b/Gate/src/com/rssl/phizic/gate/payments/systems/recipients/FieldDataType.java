package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * ������������ ����� �����.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public enum FieldDataType
{
	/**
	 * ������
	 */
	string("string"),

	/**
	 * ����
	 */
	date("date"),

	/**
	 * ����� �� ������ � ���������� ������
	 */
	number("number"),

   /**
    * ����� �����
    */
   integer("integer"),

	/**
	 * C���� � ������ ��� ������
	 */
	money("money"),

	/**
	 * ���� � ������������� ������� ���������� ��������.
	 * ��� ������ ������������ ����� 1 ��������.
	 */
	list("list"),

	/**
	 * ���� � ������������� ������� ���������� ��������.
	 * ��� ������ ������������ 0 ��� ����� ��������.
	 */
	set("set"),

	/**
	 * ���� ��� ���������� ��������
	 */
	calendar("calendar"),

	/**
	 * ������� ������
	 */
	link("link"),

	/**
	 * ���� �� ������ ������������ ������������� set'a
	 */
	graphicset("graphicset"),

	/**
	 * ���� ��� ������ "��-���"(�� ���� �������)
	 */
	choice("choice"),

	/**
	 * ���� ��� ������� email
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

		throw new IllegalArgumentException("����������� ��� ���� [" + value + "]");
	}
}