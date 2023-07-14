package com.rssl.common.forms.parsers;

import com.rssl.phizic.utils.StringHelper;

/**
 * @author Balovtsev
 * @since 14.01.2015.
 */
public class BooleanTrueIfNullParser extends BooleanParser
{
	@Override
	public Boolean parse(String value)
	{
		if (StringHelper.isEmpty(value))
		{
			return true;
		}

		return super.parse(value);
	}
}
