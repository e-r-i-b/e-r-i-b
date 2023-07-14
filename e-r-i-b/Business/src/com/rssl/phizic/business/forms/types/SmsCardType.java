package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.gate.bankroll.Card;

import java.util.List;
import java.util.ArrayList;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsCardType extends CardType
{
	public static final Type INSTANCE = new SmsCardType();
	private static final FieldValueParser defaultParser = new SmsCardParser();

	public FieldValueParser getDefaultParser()
	{
		return defaultParser;
	}
}
