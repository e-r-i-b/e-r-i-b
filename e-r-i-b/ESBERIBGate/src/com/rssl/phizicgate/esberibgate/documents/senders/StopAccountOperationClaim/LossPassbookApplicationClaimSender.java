package com.rssl.phizicgate.esberibgate.documents.senders.StopAccountOperationClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.LossPassbookApplicationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOnlineJMSDocumentSender;

/**
 * @author akrenev
 * @ created 21.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ "������ �� ����� �������������� ������"
 */

public class LossPassbookApplicationClaimSender extends AbstractOnlineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public LossPassbookApplicationClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new LossPassbookApplicationClaimProcessor(getFactory(), (LossPassbookApplicationClaim) document, getServiceName(document)));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("��������� �������� ������ �� ����� �������������� ������ �� ��������������");
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������ �� ����� �������������� ������ �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������ �� ����� �������������� ������ �� ��������������");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� ������ �� ����� �������������� ������ �� ��������������");
	}
}
