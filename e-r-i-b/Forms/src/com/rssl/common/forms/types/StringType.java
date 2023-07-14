package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.NullParser;
import com.rssl.common.forms.validators.FieldValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: krenev $
 * @ $Revision: 28953 $
 */

public class StringType implements Type
{
	public static final Type INSTANCE = new StringType();

	private static FieldValueParser defaultParser     = NullParser.INSTANCE;
	private static List<FieldValidator> defaultValidators = new ArrayList<FieldValidator>();
	private static final FieldValueFormatter formatter = new Formatter();

	public String getName()
	{
		return "string";
	}

	public FieldValueParser getDefaultParser()
	{
		return defaultParser;
	}

	public List<FieldValidator> getDefaultValidators()
	{
		return Collections.unmodifiableList(defaultValidators);
	}

	public FieldValueFormatter getFormatter()
	{
		return formatter;
	}

	private static class Formatter implements FieldValueFormatter
	{
		public String toSignableForm(String value)
		{
			if(value == null)
				return "";
			return value;
		}
	}
}