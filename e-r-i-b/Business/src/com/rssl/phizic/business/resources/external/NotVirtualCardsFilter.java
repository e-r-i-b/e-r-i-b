package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Balovtsev
 * @created 25.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * ������ ����������� ����������� �����. ����������� � ������� ����� ���������� ��������� �� �������������. 
 */
public class NotVirtualCardsFilter extends CardFilterBase
{
	/**
	 * ���������� true ��� false � ����������� �� ����, ����������� ��� ����� ��� ���.
	 * @param card ����������� �����
	 * @return true - ����� �� ����������� ����� ������������, false - ����� ����������� � � ������ ������������
	 */
	public boolean accept(Card card)
	{
		return !card.isVirtual();
	}
}
