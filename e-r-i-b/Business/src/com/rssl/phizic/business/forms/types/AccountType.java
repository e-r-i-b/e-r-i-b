package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.business.payments.forms.validators.UserAccountValidator;

import java.util.List;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountType implements Type
{
	public static final Type INSTANCE = new AccountType();

	private static final FieldValueParser defaultParser     = new AccountParser();
	private static final List<FieldValidator> defaultValidators = ListUtil.fromArray(new FieldValidator[]{ new UserAccountValidator()});
	private static final FieldValueFormatter formatter         = new Formatter();

	public String getName()
	{
		return "account";
	}

	public FieldValueParser getDefaultParser()
	{
		return AccountType.defaultParser;
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
