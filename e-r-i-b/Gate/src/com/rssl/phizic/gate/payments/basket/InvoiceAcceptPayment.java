package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

/**
 * @author osminin
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Оплата инвойса (счета)
 */
public interface InvoiceAcceptPayment extends CardPaymentSystemPayment
{
	/**
	 * @return идентификатор задолженности в АС AutoPay
	 */
	String getAutoInvoiceId();

	/**
	 * @return Идентификатор подписки в АС AutoPay
	 */
	String getAutoSubscriptionId();
}
