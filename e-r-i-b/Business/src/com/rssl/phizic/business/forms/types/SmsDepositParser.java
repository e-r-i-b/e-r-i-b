package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.business.smsbanking.SmsBankingService;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsDepositParser extends DepositParser
{
	private static final SmsBankingService service = new SmsBankingService();

	public Deposit parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ExternalResourceLink link = service.getResource(personData.getPerson().getLogin(), value, DepositLink.class);
			if (link == null)
			{
				// фича - если не нашли псевдоним пробуем искать вклад
				return super.parse(value);
			}
			if (!(link instanceof DepositLink))
				throw new ParseException("Неверный тип объекта. Ожидается Deposit", 0);

			DepositLink depositLink = (DepositLink) link;
			return depositLink.getDeposit();
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
