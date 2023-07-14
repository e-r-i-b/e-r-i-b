package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepositParser implements FieldValueParser<Deposit>
{
	public Deposit parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			DepositLink link = personData.getDeposit(Long.valueOf(value));
			Deposit deposit = link.getDeposit();
			if (deposit == null)
			{
				throw new BusinessLogicException("Депозит [id=" + value + "] не найден");
			}
			return deposit;
		}
		catch (BusinessException be)
		{
			throw new ParseException(be.getMessage(), 0);
		}
		catch (BusinessLogicException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
	}
}
