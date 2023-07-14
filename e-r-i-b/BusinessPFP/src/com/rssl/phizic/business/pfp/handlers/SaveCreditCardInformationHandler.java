package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.CreditCardFilter;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.bankroll.Card;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author akrenev
 * @ created 11.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер для запоминания типа кредитной карты
 */

public class SaveCreditCardInformationHandler extends PersonalFinanceProfileHandlerBase
{
	private static final CreditCardFilter CREDIT_CARD_FILTER = new CreditCardFilter();
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	protected void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			List<CardLink> cardLinkList = externalResourceService.getLinks(profile.getOwner(), CardLink.class);
			if (CollectionUtils.isEmpty(cardLinkList))
				return;

			for (CardLink cardLink : cardLinkList)
			{
				Card card = cardLink.getCard();
				if (CREDIT_CARD_FILTER.accept(card))
				{
					profile.setCreditCardType(card.getDescription());
					return;
				}
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка получения типа кредитной карты", e);
		}
	}
}
