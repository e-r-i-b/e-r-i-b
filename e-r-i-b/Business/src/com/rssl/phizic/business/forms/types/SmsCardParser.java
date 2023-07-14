package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.smsbanking.SmsBankingService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsCardParser extends CardParser
{
	private static final SmsBankingService service = new SmsBankingService();

	public Card parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			ExternalResourceLink link = service.getResource(personData.getPerson().getLogin(), value, CardLink.class);
			if (link == null)
			{
				// фича - если не нашли псевдоним пробуем искать карту
				return super.parse(value);
			}
			if (!(link instanceof CardLink))
				throw new ParseException("Неверный тип объекта. Ожидается Card", 0);

			CardLink cardLink = (CardLink) link;
			return cardLink.getCard();
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
