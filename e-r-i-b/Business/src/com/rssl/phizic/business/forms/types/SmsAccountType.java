package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.business.payments.forms.validators.UserAccountValidator;
import com.rssl.phizic.gate.bankroll.Account;

import java.util.List;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsAccountType extends AccountType
{
	public static final Type INSTANCE = new SmsAccountType();
	private static final FieldValueParser defaultParser = new SmsAccountParser();

	public FieldValueParser getDefaultParser()
	{
		return defaultParser;
	}
}
