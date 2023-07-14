package com.rssl.phizic.web.common.client.cards;

import com.rssl.phizic.business.resources.external.CardLink;

import java.util.List;

/**
 * @author Erkin
 * @ created 28.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Форма с информацией по основной и доп.картам
 */
public interface CardInfoForm
{
	CardLink getCardLink();

	void setCardLink(CardLink cardLink);

	List<CardLink> getAdditionalCards();

	void setAdditionalCards(List<CardLink> additionalCards);

	List<CardLink> getOtherCards();

	void setOtherCards(List<CardLink> otherCards);
}
