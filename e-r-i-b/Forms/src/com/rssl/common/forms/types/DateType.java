package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: egorova $
 * @ $Revision: 19796 $
 */

public class DateType implements Type
{
	public static final Type INSTANCE = new DateType();

	private static final FieldValueParser     defaultParser    = new DateParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new DateFieldValidator()});
	private static final FieldValueFormatter  formatter = new Formatter();

	public String getName()
	{
		return "date";
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
			if (value == null)
				return "";

			return value;
		}
	}
}