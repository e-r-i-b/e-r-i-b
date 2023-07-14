package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * Сендер перевода юридическому лицу c карты
 * на счет внутри Сбербанка России.
 *
 * @author khudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardJurIntraBankTransferSender extends TCPPaymentSender
{
	/**
	 * ctor
	 * @param factory фабрика
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CardJurIntraBankTransferSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferMethodType getXferMethod(AbstractRUSPayment document) throws GateException
	{
		if (isSameTB(document.getOffice(), document.getReceiverAccount()))
			return XferMethodType.OUR_TERBANK;

		return XferMethodType.EXTERNAL_TERBANK;
	}

	protected ReceiverType getReceiverType()
	{
		return ReceiverType.JUR;
	}
}
