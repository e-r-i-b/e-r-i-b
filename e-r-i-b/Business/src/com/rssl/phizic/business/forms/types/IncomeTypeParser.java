package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;

/**
 *
 * @author Balovtsev
 * @since  23.12.2014.
 */
public class IncomeTypeParser implements FieldValueParser<Boolean>
{
	public Boolean parse(String value) throws ParseException
	{
		if ("both".equals(value))
		{
			return null;
		}

		return StringHelper.isNotEmpty(value) && "income".equals(value);
	}
}
