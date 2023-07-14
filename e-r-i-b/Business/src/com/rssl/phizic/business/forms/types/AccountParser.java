package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class AccountParser implements FieldValueParser<Account>
{
	public Account parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			AccountLink link = personData.findAccount(value);
			return link.getAccount();
		}
		catch (BusinessException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
		catch (BusinessLogicException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
