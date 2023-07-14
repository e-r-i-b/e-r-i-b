package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.LongParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.util.List;

/**
 * @author hudyakov
 * @ created 14.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LongType implements Type
{
	private static final String TYPE  = "long";
	public static final Type INSTANCE = new LongType();

	private static final FieldValueParser     defaultParser     = new LongParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new RegexpFieldValidator("(-?\\d+)|\\d*") });
	private static final FieldValueFormatter  formatter         = new Formatter();

	public String getName()
	{
		return TYPE;
	}

	public FieldValueParser getDefaultParser()
	{
		return LongType.defaultParser;
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