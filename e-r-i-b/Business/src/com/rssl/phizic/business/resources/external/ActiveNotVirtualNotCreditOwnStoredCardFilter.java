package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * ������ �������� ������������� �����, �������� �� ������ ���������� ����, ����� ���������, ��� �����
 * ��������, �� ���������, �� �����������.
 * @author Pankin
 * @ created 11.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class ActiveNotVirtualNotCreditOwnStoredCardFilter extends CardFilterBase implements CardFilter
{
	public boolean accept(Card card)
	{
		return new ActiveNotVirtualNotCreditCardFilter().accept(card);
	}

	public boolean evaluate(Object object)
	{
		if (object instanceof CardLink)
		{
			CardLink cardLink = (CardLink) object;
			return accept(cardLink.getCard()) && new CardOwnFilter().evaluate(cardLink);
		}
		return false;
	}
}
