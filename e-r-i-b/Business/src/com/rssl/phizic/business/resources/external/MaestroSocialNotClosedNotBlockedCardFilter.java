package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;

/**
 * ������� ���������� ((9, "I", 111738, "Maestro-����������" �� ������� CardAcctDInqRq), ����� �������� � ���������������
 *
 * @author Jatsky
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 */

public class MaestroSocialNotClosedNotBlockedCardFilter extends NotBlockedCardFilter
{
	public boolean accept(Card card)
	{
		return false;
	}

	@Override
	public boolean evaluate(Object object)
	{
		if (!(object instanceof CardLink)) return false;

		CardLink cardlink = (CardLink) object;

		Card card = cardlink.getCard();
		if (card == null)
		{
			return false;
		}

		return "9".equals(card.getUNICardType()) && ("I").equals(card.getUNIAccountType()) && super.accept(card) && card.getCardState() != CardState.closed;
	}
}
