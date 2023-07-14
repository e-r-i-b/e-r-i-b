package com.rssl.common.forms.parsers;

import java.text.ParseException;

/**
 * @author hudyakov
 * @ created 14.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LongParser implements FieldValueParser<Long>
{
	public Long parse(String value) throws ParseException
	{
		try
		{
			return value == null || value.equals("") ? null : new Long(value);
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
