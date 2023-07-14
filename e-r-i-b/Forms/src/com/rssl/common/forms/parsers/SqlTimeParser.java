package com.rssl.common.forms.parsers;

import java.sql.Time;
import java.text.ParseException;

/**
 * @ created 18.08.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1865 $
 */
public class SqlTimeParser implements FieldValueParser<Time>
{
	public Time parse(String value) throws ParseException
	{
		return value == null || value.equals("") ? null : parseTime(value);
	}

	private Time parseTime(String value)
	{
		String temp = value;
		if(temp.split(":").length == 2) // если без секунд
			temp = temp + ":0";
		return Time.valueOf(temp);
	}
}