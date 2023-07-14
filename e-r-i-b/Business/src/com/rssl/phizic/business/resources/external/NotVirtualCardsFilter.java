package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.bankroll.Card;

/**
 * @author Balovtsev
 * @created 25.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр отсеивающий виртуальные карты. Применяется в случаях когда необходимо исключить их использование. 
 */
public class NotVirtualCardsFilter extends CardFilterBase
{
	/**
	 * Возвращает true или false в зависимости от того, виртуальная это карта или нет.
	 * @param card проверяемая карта
	 * @return true - карта не виртуальная можно использовать, false - карта виртуальная и её нельзя использовать
	 */
	public boolean accept(Card card)
	{
		return !card.isVirtual();
	}
}
