package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizicgate.iqwave.messaging.Constants;

/**
 * ������ ��� ������ �����������
 * @author niculichev
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentSender extends AutoPaymentSenderBase
{
	/**
	 * ctor
	 * @param factory - �������� �������
	 */
	public RefuseAutoPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * @return <��� ������� �� ���������� �������, ��� ������� ����������� �� �� ���������� ���������>
	 */
	protected Pair<String, String> getExecutionMessageName()
	{
		return new Pair<String, String>(Constants.AUTO_PAY_CANCEL_REQUEST, Constants.AUTO_PAY_CANCEL_RESPONSE);
	}

	/**
	 * ��������� ��������� �� ��������� �������
	 * @param message ���������
	 * @param payment ������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	protected void fillExecutionMessage(GateMessage message, AutoPayment payment) throws GateException, GateLogicException
	{
		message.addParameter(Constants.AUTO_PAY_CARD_NO_TEG, payment.getCardNumber());
		message.addParameter(Constants.AUTO_PAY_TEL_NO_TEG, payment.getRequisite());
		RequestHelper.appendRouteCode(message,Constants.AUTO_PAY_PROVIDER_ID_TEG, Long.valueOf(payment.getCodeService()));
	}
}
