package com.rssl.phizic.gate.payments.basket;

import com.rssl.phizic.gate.payments.longoffer.CardPaymentSystemPaymentLongOffer;

/**
 * @author osminin
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� �������� �������� �� �������������
 */
public interface CloseInvoiceSubscription extends CardPaymentSystemPaymentLongOffer
{
	/**
	 * @return true - ��� �������� �������� ���������� ������������ ����������(getLongOfferExternalId)
	 */
	boolean isRecoverAutoSubscription();
}
