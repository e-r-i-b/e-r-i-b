package com.rssl.phizic.business.smsbanking.resolver;

import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.smsbanking.pseudonyms.Pseudonym;
import com.rssl.phizic.business.smsbanking.pseudonyms.CardPseudonym;
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
public class CardResolver implements PseudonymResolver
{
	public ExternalResourceLinkBase getLink(Pseudonym pseudonym) throws BusinessException, BusinessLogicException
	{
		List<CardLink> list = PersonContext.getPersonDataProvider().getPersonData().getCards();
		for (CardLink link : list)
		{
			if (link.getExternalId().equals(pseudonym.getValue()))
			{
				return link;
			}
		}
		return null;
	}

	public Class<? extends Pseudonym> getPseudonymClass()
	{
		return CardPseudonym.class;
	}
}
