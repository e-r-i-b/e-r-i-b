package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Платёжная задача "Перевыпуск карты"
 */
public interface CardIssueTask extends PaymentTask
{
	/**
	 * Задать карту.
	 * @param cardLink - блокируемая карта (never null)
	 */
	void setCardLink(BankrollProductLink cardLink);

	/**
	 * Алиас блокируемой карты
	 * @return алиас
	 */
	String getBlockingProductAlias();

	/**
	 * Задать код блокировки.
	 * @param blockCode - код блокировки (never null)
	 */
	void setIssueCode(Integer blockCode);

	/**
	 * установить алиас блокируемого продукта
	 * @param blockingProductAlias алиас блокируемой карты
	 */
	void setBlockingProductAlias(String blockingProductAlias);
}
