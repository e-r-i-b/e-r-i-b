package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * ѕлатежна€ задача "Ѕлокировка карты"
 * @author Rtischeva
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public interface BlockingCardTask extends PaymentTask
{
	/**
	 * «адать карту.
	 * @param cardLink - блокируема€ карта (never null)
	 */
	void setCardLink(BankrollProductLink cardLink);

	/**
	 * «адать код блокировки.
	 * @param blockCode - код блокировки (never null)
	 */
	void setBlockCode(Integer blockCode);

	/**
	 * вернуть алиас блокируемого продукта
	 * @return
	 */
	String getBlockingProductAlias();

	/**
	 * установить алиас блокируемого продукта
	 * @param blockingProductAlias
	 */
	void setBlockingProductAlias(String blockingProductAlias);
}
