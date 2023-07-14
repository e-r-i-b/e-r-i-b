package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ����������� ������
 */
public interface InvoiceSubscriptionOperation extends CardPaymentSystemPaymentLongOffer
{
	/**
	 * @return ������������� ���������� ������
	 */
	Long getInvoiceSubscriptionId();
}
