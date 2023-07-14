package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * @author saharnova
 * @ created 09.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Заявка на редактирование автоподписки на инвойсы
 *
 */

public interface EditInvoiceSubscription extends CardPaymentSystemPaymentLongOffer
{
	/**
	 * @return идентификатор подписки на инвойсы, на основе которой создан платеж
	 */
	public Long getInvoiceSubscriptionId();
}
