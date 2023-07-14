package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;

/**
 * ������ �������� ����������� ���� c ����� �� ����
 * � ������ ���� ����� ��������� ������� ������.
 *
 * @author khudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardRUSPaymentSender extends TCPPaymentSender
{
	/**
	 * ctor
	 * @param factory �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CardRUSPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferMethodType getXferMethod(AbstractRUSPayment document)
	{
		return XferMethodType.EXTERNAL_BANK;
	}

	protected ReceiverType getReceiverType()
	{
		return ReceiverType.PHIZ;
	}
}
