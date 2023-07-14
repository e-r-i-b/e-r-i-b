package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRq;

/**
 * @author akrenev
 * @ created 04.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������.
 */

class CardToAccountPhizIntraBankTransferProcessor extends CardToAccountPhizBankTransferProcessorBase<CardRUSPayment>
{
	/**
	 * �����������
	 * @param factory �������� �������
	 * @param document ��������
	 * @param serviceName ��� �������
	 */
	CardToAccountPhizIntraBankTransferProcessor(GateFactory factory, CardRUSPayment document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	@Override
	protected OperNameType getOperationName(CardRUSPayment document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferMethodType getXferMethodType(CardRUSPayment document)
	{
		return XferMethodType.EXTERNAL_BANK;
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, CardRUSPayment document){}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardRUSPayment))
			throw new GateException("�������� ��� ���������, ������ ���� - CardRUSPayment.");

		CardRUSPayment transfer = (CardRUSPayment) document;
		return transfer.getChargeOffCurrency();
	}
}
