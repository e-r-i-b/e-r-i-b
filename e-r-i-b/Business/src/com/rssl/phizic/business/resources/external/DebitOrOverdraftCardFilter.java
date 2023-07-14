package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;

/**
 * @author EgorovaA
 * @ created 06.03.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ ��������� � ������������ ����
 */
public class DebitOrOverdraftCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		return CardType.debit == card.getCardType() || CardType.overdraft == card.getCardType();
	}
}
