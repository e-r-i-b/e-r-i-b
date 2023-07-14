package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.business.smsbanking.SmsBankingService;
import com.rssl.phizic.business.smsbanking.pseudonyms.PseudonymService;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.AccessPseudonymException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 07.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsAccountParser extends AccountParser
{
	private static final SmsBankingService service = new SmsBankingService();
	private static final PseudonymService pseudonymService = new PseudonymService();

	public Account parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ExternalResourceLink link = service.getResource(personData.getPerson().getLogin(), value, AccountLink.class);
			if (link == null)
			{
				// фича - если не нашли псевдоним пробуем искать счет
				return super.parse(value);
			}

			Pseudonym pseudo = pseudonymService.findByPseudonymName(personData.getPerson().getLogin(), value);
			if (!pseudo.isHasAccess())
			{
				throw new AccessPseudonymException("Доступ к псевдониму " + value  + " запрещен.");
			}
			
			if (!(link instanceof AccountLink))
				throw new ParseException("Неверный тип объекта. Ожидается Account", 0);

			AccountLink accountLink = (AccountLink) link;
			return accountLink.getAccount();
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