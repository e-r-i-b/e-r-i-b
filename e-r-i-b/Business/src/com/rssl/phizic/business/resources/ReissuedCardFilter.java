package com.rssl.phizic.business.resources;

import com.rssl.phizic.business.cards.CardImpl;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

/**
 * Фильтр карт для отображения на странице Перевыпуск карт.
 * Этот фильтр должен удовлетворять условиям:
 * все действующие карты клиента,
 * заблокированные карты, имеющие следующие типы блокировки:
 * -Карта украдена
 * -Карта утеряна
 * -По инициативе держателя
 * -Изъята банком
 * -Карта утрачена
 * -Компрометация
 *
 * При перевыпуске карт необходимо ограничить перевыпуск следующих типов карт:
 * 1)	Перевыпуск кредитных карт (CardType= CC)
 * 2)	Перевыпуск Instant Issue карт(Momentum). (UNICardType=7 или  UNICardType=8 и UNIAcctType= F )
 * 3)	Перевыпуск карт УЭК. (UNICardType=27)
 * 4)	Перевыпуск карт ПРО100 (UNICardType=14 или UNICardType=15 или UNICardType=17 или UNICardType=18)
 *
 * @author bogdanov
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedCardFilter extends ActiveCardWithArrestedAccountFilter
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static CardFilter blockedFilter = new BlockedCardFilter();
	private static CardFilter unallowedCardFilter = new UnallowedCardForReissueCardFilter();

	@Override
	public boolean evaluate(Object object)
	{
		if (!(object instanceof CardLink))
			return false;

		CardLink cardLink = (CardLink) object;
		Card card = cardLink.getCard();

		return unallowedCardFilter.evaluate(object) && (isBlocked(card) || super.evaluate(object));
	}

	protected boolean isBlocked(Card card)
	{
		if (!blockedFilter.accept(card))
			return false;

		if (card.getStatusDescExternalCode() == null)
			return true;

		switch (card.getStatusDescExternalCode())
		{
			case S_B:
			case S_C:
			case S_P:
			case S_Y:
			case S_S:
			case S_L:
			case S_K:
			case S_J:
				return true;
			default:
				return false;
		}
	}
}