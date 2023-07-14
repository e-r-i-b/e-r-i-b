package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * «а€вка на закрытие подписки на задолженности
 */
public interface CloseInvoiceSubscription extends CardPaymentSystemPaymentLongOffer
{
	/**
	 * @return true - при успешном закрытии необходимо восстановить автоплатеж(getLongOfferExternalId)
	 */
	boolean isRecoverAutoSubscription();
}
