package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.MaskUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class CardType implements Type
{
	public static final Type INSTANCE = new CardType();

	private static final FieldValueParser defaultParser     = new CardParser();
	private static final List<FieldValidator> defaultValidators = new ArrayList<FieldValidator>();
	private static final FieldValueFormatter formatter         = new Formatter();

	public String getName()
	{
		return "card";
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
			return MaskUtil.getCutCardNumberForLog(value);
		}
	}
}
