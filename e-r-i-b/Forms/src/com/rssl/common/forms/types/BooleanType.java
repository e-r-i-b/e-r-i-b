package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.BooleanParser;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: pakhomova $
 * @ $Revision: 9380 $
 */
public class BooleanType implements Type
{
	public static final Type INSTANCE = new BooleanType();

	private static FieldValueParser defaultParser     = new BooleanParser();
	private static List<FieldValidator> defaultValidators = new ArrayList<FieldValidator>();
	private static final FieldValueFormatter formatter = new Formatter();

	public String getName()
	{
		return "boolean";
	}

	public FieldValueParser getDefaultParser()
	{
		return defaultParser;
	}

	public List<FieldValidator> getDefaultValidators()
	{
		return defaultValidators;
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