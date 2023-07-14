package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * Сендер перевода юридическому лицу c карты
 * на счет в другой банк через платежную систему России.
 *
 * @author khudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardJurTransferSender extends TCPPaymentSender
{
	/**
	 * ctor
	 * @param factory фабрика
	 * @throws GateException
	 */
	public CardJurTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferMethodType getXferMethod(AbstractRUSPayment document)
	{
		return XferMethodType.EXTERNAL_BANK;
	}

	protected ReceiverType getReceiverType()
	{
		return ReceiverType.JUR;
	}
}
