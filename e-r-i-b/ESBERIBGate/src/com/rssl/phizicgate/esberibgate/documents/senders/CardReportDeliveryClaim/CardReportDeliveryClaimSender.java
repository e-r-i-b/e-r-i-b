package com.rssl.phizicgate.esberibgate.documents.senders.CardReportDeliveryClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.CardReportDeliveryClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOfflineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 08.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ �� ��������� ���������� �������� �� �����
 */

public class CardReportDeliveryClaimSender extends AbstractOfflineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory �������
	 */
	public CardReportDeliveryClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new CardReportDeliveryClaimProcessor(getFactory(), (CardReportDeliveryClaim) document));
	}

	public void repeatSend(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� �������� ������ �� ��������� ���������� �������� �� ����� �� ��������������");
	}

	public void rollback(WithdrawDocument document)
	{
		throw new UnsupportedOperationException("����� ������ �� ��������� ���������� �������� �� ����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������ �� ��������� ���������� �������� �� ����� �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������ �� ��������� ���������� �������� �� ����� �� ��������������");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� ������ �� ��������� ���������� �������� �� ����� �� ��������������");
	}
}
