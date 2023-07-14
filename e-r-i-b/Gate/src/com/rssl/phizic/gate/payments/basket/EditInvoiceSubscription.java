package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * @author saharnova
 * @ created 09.06.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� �������������� ������������ �� �������
 *
 */

public interface EditInvoiceSubscription extends CardPaymentSystemPaymentLongOffer
{
	/**
	 * @return ������������� �������� �� �������, �� ������ ������� ������ ������
	 */
	public Long getInvoiceSubscriptionId();
}
