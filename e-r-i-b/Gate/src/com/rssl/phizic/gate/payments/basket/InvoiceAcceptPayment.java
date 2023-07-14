package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;

/**
 * @author osminin
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� (�����)
 */
public interface InvoiceAcceptPayment extends CardPaymentSystemPayment
{
	/**
	 * @return ������������� ������������� � �� AutoPay
	 */
	String getAutoInvoiceId();

	/**
	 * @return ������������� �������� � �� AutoPay
	 */
	String getAutoSubscriptionId();
}
