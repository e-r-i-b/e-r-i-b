package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * Проверка проходит ли карта по ограничению.
 *
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public interface CardRestriction extends Restriction
{
	/**
	 * Подходит ли карта под критерии
	 * @param card см. {@link Card}
	 * @return true - подходит, false - не подходит
	 */
	boolean accept(Card card);
}
