package com.rssl.phizicgate.esberibgate.documents.senders.BankTransfer;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.CardRUSPayment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� "������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������."
 */

public class CardToAccountPhizIntraBankTransferSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public CardToAccountPhizIntraBankTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new CardToAccountPhizIntraBankTransferProcessor(getFactory(), (CardRUSPayment) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("��������� �������� ������� \"������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������.\" �� ��������������");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������� \"������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������.\" �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������� \"������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������.\" �� ��������������");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� ������� \"������� ����������� ���� c ����� �� ���� � ������ ���� ����� ��������� ������� ������.\" �� ��������������");
	}
}
