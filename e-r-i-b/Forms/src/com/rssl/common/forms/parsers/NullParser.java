package com.rssl.common.forms.parsers;

/**
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: krenev $
 * @ $Revision: 28953 $
 */

public class NullParser implements FieldValueParser<String>
{
	public static final FieldValueParser INSTANCE = new NullParser();

	public String parse(String value)
    {
        return value;
    }
}
