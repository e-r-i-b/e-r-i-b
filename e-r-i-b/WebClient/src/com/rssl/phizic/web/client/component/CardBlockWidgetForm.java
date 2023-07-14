package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.CardAbstract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 08.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Виджет-блок с картами клиента
 */
public class CardBlockWidgetForm extends ProductBlockWidgetForm
{
	private List<CardLink> cards = new ArrayList<CardLink>();

	private Map<CardLink, CardAbstract> cardAbstract;

	
	private boolean isAllCardDown;

	private List<Long> showCardLinkIds;

	///////////////////////////////////////////////////////////////////////////

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public Map<CardLink, CardAbstract> getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(Map<CardLink, CardAbstract> cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}
	
	/**
	 *
	 * @return true - если не удалось получит информацию по всем картам, т.к. были ошибки бэк офиса.
	 */
	public boolean isAllCardDown()
	{
		return isAllCardDown;
	}

	public void setAllCardDown(boolean isAllCardDown)
	{
		this.isAllCardDown = isAllCardDown;
	}

	public List<Long> getShowCardLinkIds()
	{
		return showCardLinkIds;
	}

	public void setShowCardLinkIds(List<Long> showCardLinkIds)
	{
		this.showCardLinkIds = showCardLinkIds;
	}
}
