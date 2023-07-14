package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;

/**
 * @author Erkin
 * @ created 20.04.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Платёжная задача "Перевод по номеру карты"
 */
public interface CardTransferTask extends PaymentTask
{
	/**
	 * Задать источник списания
	 * @param senderProduct - линк карты/счёта списания (never null)
	 */
	@MandatoryParameter
	void setSenderProduct(BankrollProductLink senderProduct);

	/**
	 * Задать приёмник зачисления
	 * @param receiverCardNumber - номер карты зачисления (never null nor empty)
	 */
	@MandatoryParameter
	void setReceiverCardNumber(String receiverCardNumber);

	/**
	 * Задать сумму перевода.
	 * @param amount - сумма списания в рублях (never null)
	 */
	@MandatoryParameter
	void setAmount(Money amount);
}
