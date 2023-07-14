package com.rssl.phizicgate.esberibgate.ws.jms.segment.light.common.sender;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ������ �������� � ����� �� �����
 */

public class CardToCardTransferSenderBase<D extends CardsTransfer> extends AbstractOnlineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	protected CardToCardTransferSenderBase(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		//noinspection unchecked
		process(new CardToCardTransferProcessor<D>((D) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("��������� �������� ������� \"������� � ����� �� �����\" �� ��������������");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������� \"������� � ����� �� �����\" �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������� \"������� � ����� �� �����\" �� ��������������");
	}

	public void validate(GateDocument document){}
}