package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;

/**
 * @author Gulov
 * @ created 11.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Платёжная задача "Блокировка счёта"
 */
public interface LossPassbookTask extends PaymentTask
{
	/**
	 * Задать счет.
	 * @param accountLink - блокируемый счет (never null)
	 */
	void setLink(BankrollProductLink accountLink);

	/**
	 * Задать код блокировки.
	 * @param blockCode - код блокировки (can be null)
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
