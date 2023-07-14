package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.IntParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author Dorzhinov
 * @ created 27.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class IntType implements Type
{
	public static final Type INSTANCE = new IntType();

	private static final FieldValueParser defaultParser     = new IntParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new RegexpFieldValidator("(-?\\d+)|\\d*") });
	private static final FieldValueFormatter  formatter         = new Formatter();

	public String getName()
	{
		return "int";
	}

	public FieldValueParser getDefaultParser()
	{
		return IntType.defaultParser;
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
