package com.rssl.phizicgate.iqwave.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;

/**
 * При передаче запроса на оплату к идентификатору слева добавляется код подуслуги,
 * введненый в специальное скрытое от пользователя поля.
 */

public class SubServiceCodeWithIdentifierPaymentSender extends SimplePaymentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public SubServiceCodeWithIdentifierPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	public Object getIdentifier(CardPaymentSystemPayment payment) throws GateException
	{
		try
		{
			String identifier = super.getIdentifier(payment).toString();
			Field r192025125 = BillingPaymentHelper.getFieldById(payment.getExtendedFields(), Constants.R192025125);
			return r192025125.getValue().toString() + identifier;
		}
		catch(DocumentException e)
		{
			throw new GateException(e);
		}
	}
}
