package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.context.PersonContext;

import java.util.List;

/**
 * @author eMakarov
 * @ created 09.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class CardPseudonym extends Pseudonym
{
	private CardLink cardLink;

	public ExternalResourceLink getLink() throws BusinessException, BusinessLogicException
	{
		if (cardLink == null)
		{
			List<CardLink> links = PersonContext.getPersonDataProvider().getPersonData().getCards();
			for (CardLink cLink : links)
			{
				if (cLink.getExternalId().equals(getValue()))
				{
					cardLink = cLink;
					break;
				}
			}
		}
		return cardLink;
	}

	public String getNumber() throws BusinessException, BusinessLogicException
	{
		if (cardLink == null)
		{
			getLink();
		}
		return cardLink.getNumber();
	}
}
