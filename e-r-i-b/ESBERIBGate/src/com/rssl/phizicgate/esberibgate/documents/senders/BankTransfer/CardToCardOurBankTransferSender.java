package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.payments.ExternalCardsTransferToOurBank;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender.CardToCardTransferSenderBase;

/**
 * @author akrenev
 * @ created 09.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� "������� � ����� �� ����� ��������� (�� ���)"
 */

public class CardToCardOurBankTransferSender extends CardToCardTransferSenderBase<ExternalCardsTransferToOurBank>
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public CardToCardOurBankTransferSender(GateFactory factory)
	{
		super(factory);
	}
}
