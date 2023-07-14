package com.rssl.phizic.web.common.client.cards;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.operations.card.GetCardsOperation;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 28.04.2011
 * @ $Author$
 * @ $Revision$
 */
public class CardInfoFormHelper
{
	public static void setAdditionalCards(CardInfoForm form, GetCardsOperation operationCards)
	{
		// карта, по которой просматривается детальная информация
		CardLink cardLink = form.getCardLink();

		//Получаем ВСЕ карты пользователя
   		List<CardLink> personCardLinks = operationCards.getPersonCardLinks();

		List<CardLink> result = new ArrayList<CardLink>();
		result.addAll(personCardLinks);

		//Если карта, по которой запрашиваем инфу основная и не виртуальная, получаем все дополнительные к ней карты
		Card card = cardLink.getCard();
		if (!card.isVirtual() && card.isMain())
		{
			List<CardLink> additionalCards = operationCards.getAdditionalCards(Collections.singletonList(cardLink));
			form.setAdditionalCards(additionalCards);
			if (!CollectionUtils.isEmpty(additionalCards) && !CollectionUtils.isEmpty(result))
				result.removeAll(additionalCards);
		}

		if (result != null)
		{
			result.remove(cardLink);
			form.setOtherCards(result);
		}
	}
}
