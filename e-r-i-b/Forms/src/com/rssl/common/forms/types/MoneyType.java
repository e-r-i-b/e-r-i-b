package com.rssl.common.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.phizic.utils.ListUtil;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: pakhomova $
 * @ $Revision: 9380 $
 */

public class MoneyType implements Type
{
	private static FieldValueParser defaultParser     = new BigDecimalParser();
	private static List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new MoneyFieldValidator() });
	private static final FieldValueFormatter formatter = new Formatter();

	public static final Type INSTANCE = new MoneyType();

	public String getName()
	{
		return "money";
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
			
//			BigDecimal bd = (BigDecimal) value;
//			return bd.stripTrailingZeros().toPlainString();
			return value;
		}
	}
}