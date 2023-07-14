package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * Домолинк Рязань –
 * идентификатор в формате 10d (добивается лидирующими нулями, если надо).
 *
 * Код сервиса (маршрута)
 * Символьная кодировка	Цифровая кодировка
 *       CF	              114
 */
public class DomolinkSimplePaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public DomolinkSimplePaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		String identifier = super.getIdentifier(payment).toString();
		String fullIdentifier = "106" + StringHelper.appendLeadingZeros(identifier, 10);
		return fullIdentifier;
	}
}
