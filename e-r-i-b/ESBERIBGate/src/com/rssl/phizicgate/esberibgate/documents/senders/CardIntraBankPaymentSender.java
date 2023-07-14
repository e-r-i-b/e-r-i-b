package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;

/**
 * ������ �������� ����������� ���� c ����� �� ����
 * ������ ��������� ������
 *
 * @author khudyakov
 * @ created 21.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardIntraBankPaymentSender extends TCPPaymentSender
{
	/**
	 * ctor
	 * @param factory �������
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 */
	public CardIntraBankPaymentSender(GateFactory factory) throws GateException
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
		return ReceiverType.PHIZ;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if(!(document instanceof CardIntraBankPayment))
			throw new GateException("������������ ��� ���������, �������� ��������� CardIntraBankPayment.");

		CardIntraBankPayment transfer = (CardIntraBankPayment) document;
		if(!isLongOfferMode())
			return;

		LongOffer longOffer = (LongOffer) document;
		if(!isSameTB(transfer.getOffice(), transfer.getReceiverAccount()))
		{
			// �������� �� ��, ��� � ���������� �������, ������� � ����� ����� "����� ����������� ��� ���������� ������� �� ����� ���������� ��
			// ��������� �����" ����������� ������ ������ ��.
			if(longOffer.getSumType() == SumType.REMAIND_IN_RECIP)
			{
				throw  new GateLogicException(REMAIND_IN_RECIP_MESSAGE);
			}

			// �������� �� ��, ��� � ���������� �������, ������� � ����� ����� " �� ����� ���������� �� ����� ����������"
			//����������� ������ ������ ��.
			if(longOffer.getSumType() == SumType.OVER_DRAFT)
			{
				throw  new GateLogicException(OVER_DRAFT_MESSAGE);
			}

		}
	}

}