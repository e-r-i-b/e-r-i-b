package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.common.forms.parsers.FieldValueParser;

import java.text.ParseException;

/**
 * @author gladishev
 * @ created 01.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class CardParser implements FieldValueParser<Card>
{
	public Card parse(String value) throws ParseException
	{
		if (value == null || value.equals("")) return null;
		try
		{
			PersonDataProvider provider = PersonContext.getPersonDataProvider();
			PersonData personData = provider.getPersonData();
			CardLink link = personData.getCard(Long.parseLong(value));
 			return link.getCard();
		}
		catch (BusinessException be)
		{
			throw new ParseException(be.getMessage(), 0);
		}
	}
}
