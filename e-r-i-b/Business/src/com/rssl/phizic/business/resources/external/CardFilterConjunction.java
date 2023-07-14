package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Erkin
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� "�" ��� ����-���������
 */
public class CardFilterConjunction extends CompositeCardFilterBase
{
	/**
	 * default ctor
	 */
	public CardFilterConjunction() {}

	/**
	 * ����������� �� ������� �������
	 * @param filters
	 */
	public CardFilterConjunction(CardFilter... filters)
	{
		super(filters);
	}

	public final boolean accept(Card card)
	{
		for (CardFilter filter : getFilters()) {
			if (!filter.accept(card))
				return false;
		}
		return true;
	}
}
