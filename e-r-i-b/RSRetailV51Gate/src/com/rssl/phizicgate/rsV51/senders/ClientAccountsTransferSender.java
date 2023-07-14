package com.rssl.phizicgate.rsV51.senders;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.rsV51.demand.PaymentDemand;
import com.rssl.phizicgate.rsV51.demand.PaymentDemandBase;
import com.rssl.phizicgate.rsV51.bankroll.AccountImpl;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class ClientAccountsTransferSender extends AbstractPaymentSender
{
	/**
	 * ������� ������
	 * @return ������
	 */
	protected PaymentDemandBase createDemand()
	{
		return new PaymentDemand();
	}

	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof ClientAccountsTransfer))
			throw new GateLogicException("�������� ��� �������, ������ ���� - ClientAccountsTransfer.");
		ClientAccountsTransfer clientAccountsTransfer = (ClientAccountsTransfer) document;
/*
��������� ����
	Account, ClientCode, DocumentDate, IsResident,
	CurrencyCode, IsCur, SummaOut,
	OperationType, OperationKind,
*/
		super.fillDemand(demand, clientAccountsTransfer);

		demand.setReceiverAccount(clientAccountsTransfer.getReceiverAccount());
		//������ �� ���������� � RS-Retail Pervasive:��� ������ ����� ����������	Psdepdoc.dbt	CodeCur_Receiver		=$(CodeCurReceiver)	����� Code_Currency
		demand.setReceiverCurrencyCode(demand.getCurrencyCode());
//todo ������� Ground � gate-documents-config.xml
		demand.setGround("�/� �� �����. ��-�� �� "+ DateHelper.toString(demand.getDocumentDate())+" �����. ������� ����������� ������� ��� ������ ���");

		AccountImpl account = getAccount(demand.getReceiverAccount(), demand.getClientCode());
		demand.setReceiverOffice( account.getBranch() );
		demand.setReceiverCorAccount(account.getDescription());
	}
}
