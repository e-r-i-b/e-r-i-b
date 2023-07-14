package com.rssl.phizicgate.esberibgate.documents.senders.GetPrivateOperationScanClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.GetPrivateOperationScanClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOfflineJMSDocumentSender;

/**
 * @author komarov
 * @ created 17.04.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ �� ��������� ����� ��������� �� ����������� �����
 */

public class GetPrivateOperationScanClaimSender extends AbstractOfflineJMSDocumentSender
{
	/**
	 * �����������
	 * @param factory �������
	 */
	public GetPrivateOperationScanClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		process(new GetPrivateOperationScanClaimProcessor(getFactory(), (GetPrivateOperationScanClaim) document));
	}

	public void repeatSend(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� �������� ������ �� ��������� ����� ��������� �� ����������� ����� �� ��������������");
	}

	public void rollback(WithdrawDocument document)
	{
		throw new UnsupportedOperationException("����� ������ �� ��������� ����� ��������� �� ����������� ����� �� ��������������");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("���������� ������ �� ��������� ����� ��������� �� ����������� ����� �� ��������������");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("������������� ������ �� ��������� ����� ��������� �� ����������� ����� �� ��������������");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("��������� ������ �� ��������� ����� ��������� �� ����������� ����� �� ��������������");
	}
}
