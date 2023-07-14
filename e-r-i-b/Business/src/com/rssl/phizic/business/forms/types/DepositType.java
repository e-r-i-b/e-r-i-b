package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.business.payments.forms.validators.UserDepositValidator;
import com.rssl.phizic.gate.deposit.Deposit;

import java.util.List;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepositType implements Type
{
	public static final Type INSTANCE = new DepositType();

	private static final FieldValueParser defaultParser     = new DepositParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new UserDepositValidator()});
	private static final FieldValueFormatter formatter         = new Formatter();

	public String getName()
	{
		return "deposit";
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

//			Deposit deposit = (Deposit) value;
//			return deposit.getId();
			return value;
		}
	}
}
