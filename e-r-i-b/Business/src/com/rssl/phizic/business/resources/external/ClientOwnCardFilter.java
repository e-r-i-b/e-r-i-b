package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;

/**
 * ������, ������������ ��� �����, ������� �������� ��������� ��� ��������������� � ����� CLIENTTOCLIENT ��� OTHERTOCLIENT.
 *
 * @author bogdanov
 * @ created 23.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ClientOwnCardFilter extends CardFilterBase
{
	public boolean accept(Card card)
	{
		if (card.getAdditionalCardType() == null ||
			card.getAdditionalCardType() == AdditionalCardType.CLIENTTOCLIENT ||
			card.getAdditionalCardType() == AdditionalCardType.OTHERTOCLIENT)
		{
			return true;
		}
		return false;
	}
}
