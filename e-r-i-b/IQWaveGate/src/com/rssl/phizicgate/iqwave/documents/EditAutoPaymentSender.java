package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * —ендер редактировани€ автоплатежа
 * @author niculichev
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditAutoPaymentSender extends AutoPaymentSenderBase
{
	/**
	 * ctor
	 * @param factory - гейтова€ фабрика
	 */
	public EditAutoPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @return <им€ запроса на исполнение платежа, им€ запроса присылаемое ¬— на исполнение документа>
	 */
	protected Pair<String, String> getExecutionMessageName()
	{
		return new Pair<String, String>(Constants.AUTO_PAY_CORRECTION_REQUEST, Constants.AUTO_PAY_CORRECTION_RESPONSE);
	}

	/**
	 * «аполнить сообщение на исполение платежа
	 * @param message сообщение
	 * @param payment платеж
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected void fillExecutionMessage(GateMessage message, AutoPayment payment) throws GateException, GateLogicException
	{
	    super.fillExecutionMessage(message, payment);
		message.addParameter(Constants.AUTO_PAY_FRIENDLY_NAME_TEG, payment.getFriendlyName());
	}

}
