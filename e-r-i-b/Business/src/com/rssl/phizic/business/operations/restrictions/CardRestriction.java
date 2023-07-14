package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * �������� �������� �� ����� �� �����������.
 *
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CardRestriction extends Restriction
{
	/**
	 * �������� �� ����� ��� ��������
	 * @param card ��. {@link Card}
	 * @return true - ��������, false - �� ��������
	 */
	boolean accept(Card card);
}
