package com.rssl.phizic.gate.documents.attribute;

import com.rssl.phizic.utils.StringHelper;

/**
 * “ип доп. атрибута
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public enum Type
{
	STRING("string"),
	BOOLEAN("boolean"),
	DATE("date"),
	DATE_TIME("dateTime"),
	DECIMAL("decimal"),
	INTEGER("int"),
	LONG("long");

	private String type;

	Type(String type)
	{
		this.type = type;
	}

	/**
	 * ¬ернуть тип атрибута
	 * @param type провер€емый тип
	 * @return тип
	 */
	public Type fromValue(String type)
	{
		if (StringHelper.isEmpty(type))
			throw new NullPointerException("тип атрибута не может быть null");

		if (STRING.type.equals(type))
			return STRING;
		if (BOOLEAN.type.equals(type))
			return BOOLEAN;
		if (DATE.type.equals(type))
			return DATE;
		if (DATE_TIME.type.equals(type))
			return DATE_TIME;
		if (DECIMAL.type.equals(type))
			return DECIMAL;
		if (INTEGER.type.equals(type))
			return INTEGER;
		if (LONG.type.equals(type))
			return LONG;

		throw new IllegalArgumentException("некорректный тип доп. атрибута");
	}







}
