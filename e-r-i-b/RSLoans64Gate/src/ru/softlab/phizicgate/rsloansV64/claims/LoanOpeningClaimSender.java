package ru.softlab.phizicgate.rsloansV64.claims;

import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.LoanOpeningClaim;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 10.01.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� �������� ������ �� ������ � RS-Loans
 */
public class LoanOpeningClaimSender implements DocumentSender
{
	private Map<String, ?> params = null;

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//���������������� �������� �� ����������
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//����� ���� �� ��������������.
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if(! (document instanceof LoanOpeningClaim))
			throw new GateException("�������� ��� ������. ������ ���� LoanOpeningClaim. ��������� ���������������� ���� gate-documents-config.xml");

		LoanOpeningClaim claim = (LoanOpeningClaim)document;

		LoanClaimSendService sendService = new LoanClaimSendService();
		try
		{
			sendService.send(claim);
		}
		catch(GateException ex)
		{
			throw new GateException("�� ������� ��������� ������ � id:"+claim   .getId(),ex);
		}
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����������� ��������� �������� ������� �� �����������");
	}

	public void setParameters(Map<String, ?> params)
	{
		this.params = params;
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����������� ������������� ������� �� �����������");
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("����������� ��������� ������� �� �����������");
	}
}
