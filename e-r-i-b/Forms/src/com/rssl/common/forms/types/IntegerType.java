package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.IntegerParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;
import java.math.BigInteger;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: pakhomova $
 * @ $Revision: 9380 $
 */

public class IntegerType implements Type
{
	public static final Type INSTANCE = new IntegerType();

	private static final FieldValueParser     defaultParser     = new IntegerParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new RegexpFieldValidator("(-?\\d+)|\\d*") });
	private static final FieldValueFormatter  formatter         = new Formatter();

	public String getName()
	{
		return "integer";
	}

	public FieldValueParser getDefaultParser()
	{
		return IntegerType.defaultParser;
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