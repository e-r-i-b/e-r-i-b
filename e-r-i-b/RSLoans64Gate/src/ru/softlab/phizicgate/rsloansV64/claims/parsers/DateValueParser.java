package ru.softlab.phizicgate.rsloansV64.claims.parsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Krenev
 * @ created 23.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class DateValueParser implements ValueParser
{
	private static final DateFormat fromFormat = new SimpleDateFormat("yyyy.MM.dd");
	private static final DateFormat toFormat = new SimpleDateFormat("dd.MM.yyyy");

	private DateFormat getFromFormat()
	{
		return (DateFormat)fromFormat.clone();
	}

	private DateFormat getToFormat()
	{
		return (DateFormat)toFormat.clone();
	}

	public String parse(String value)
	{
		if (value == null)
		{
			return null;
		}
		try
		{
			return getToFormat().format(getFromFormat().parse(value));
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
