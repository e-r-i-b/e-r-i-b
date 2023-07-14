package com.rssl.common.forms.parsers;

import java.text.ParseException;

/**
 * @author Dorzhinov
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class IntParser implements FieldValueParser<Integer>
{
	public Integer parse(String value) throws ParseException
	{
		try
		{
			return value == null || value.equals("") ? null : new Integer(value);
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
