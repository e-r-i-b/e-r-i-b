package com.rssl.common.forms.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kosyakov
 * @ created 14.12.2005
 * @ $Author: barinov $
 * @ $Revision: 31481 $
 */
public class DateParser implements FieldValueParser<Date>
{
	private final DateFormat format;

	private DateFormat getDateFormat()
	{
		return (DateFormat)format.clone();
	}
	/**
	 * Формат ДД.ММ.ГГГГ
	 */
	public DateParser()
	{
		format = DateFormat.getDateInstance(DateFormat.SHORT);
		format.setLenient(false);
	}

	/**
	 *  @param  formatString формат даты\времени
	 */
	public DateParser(String formatString)
	{
		format = new SimpleDateFormat(formatString);
		format.setLenient(false);
	}

	public Date parse ( String value ) throws ParseException
	{
		return value==null || value.equals("") ? null : getDateFormat().parse(value);
	}
}
