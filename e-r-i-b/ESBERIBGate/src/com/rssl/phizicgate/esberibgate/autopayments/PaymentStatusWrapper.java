package com.rssl.phizicgate.esberibgate.autopayments;

import com.rssl.phizic.gate.autopayments.PaymentStatus;
import com.rssl.phizicgate.esberibgate.ws.generated.PaymentStatusASAP_Type;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bogdanov
 * @ created 06.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * переводит статусы платежей по подписке из шины в статусы платеджей по подписке на автоплатеж.
 */

public class PaymentStatusWrapper
{
	private static final Map<PaymentStatusASAP_Type, PaymentStatus> paymentStatuses = new HashMap<PaymentStatusASAP_Type, PaymentStatus>();

	static
	{
		paymentStatuses.put(PaymentStatusASAP_Type.New,         PaymentStatus.NEW);
		paymentStatuses.put(PaymentStatusASAP_Type.Canceled,    PaymentStatus.CANCELED);
		paymentStatuses.put(PaymentStatusASAP_Type.Done,        PaymentStatus.DONE);
	}

	public static PaymentStatus getPaymentStatus(PaymentStatusASAP_Type paymentStatus_type)
	{
		return paymentStatuses.get(paymentStatus_type);
	}
}
