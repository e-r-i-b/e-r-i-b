package com.rssl.common.forms.parsers;

import java.math.BigInteger;
import java.text.ParseException;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 1849 $
 */

public class IntegerParser implements FieldValueParser<BigInteger>
{
	public BigInteger parse(String value) throws ParseException
	{
		try
		{
			return value == null || value.equals("") ? null : new BigInteger(value);
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}