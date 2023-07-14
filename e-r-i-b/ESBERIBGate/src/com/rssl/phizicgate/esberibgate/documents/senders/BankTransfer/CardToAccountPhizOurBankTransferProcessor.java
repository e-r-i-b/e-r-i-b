package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardIntraBankPayment;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.OperNameType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.XferAddRq;

/**
 * @author akrenev
 * @ created 04.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �������� ����������� ���� c ����� �� ���� � ������ ���� ������ ��������� ������.
 */

class CardToAccountPhizOurBankTransferProcessor extends CardToAccountPhizBankTransferProcessorBase<CardIntraBankPayment>
{
	/**
	 * �����������
	 * @param factory �������� �������
	 * @param document ��������
	 * @param serviceName ��� �������
	 */
	CardToAccountPhizOurBankTransferProcessor(GateFactory factory, CardIntraBankPayment document, String serviceName)
	{
		super(factory, document, serviceName);
	}

	@Override
	protected OperNameType getOperationName(CardIntraBankPayment document)
	{
		return getOperationName();
	}

	@Override
	protected String getMonitoringDocumentType()
	{
		return getOperationName().value();
	}

	@Override
	protected XferMethodType getXferMethodType(CardIntraBankPayment document) throws GateException
	{
		return BackRefInfoRequestHelper.isSameTB(document.getOffice(), document.getReceiverAccount())? XferMethodType.OUR_TERBANK: XferMethodType.EXTERNAL_TERBANK;
	}

	@Override
	protected void updateDocumentRequestData(XferAddRq request, CardIntraBankPayment document){}

	protected Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof CardIntraBankPayment))
			throw new GateException("�������� ��� ���������, ������ ���� - CardIntraBankPayment.");

		CardIntraBankPayment transfer = (CardIntraBankPayment) document;
		return transfer.getChargeOffCurrency();
	}
}
