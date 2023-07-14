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
	 * создать заявку
	 * @return заявка
	 */
	protected PaymentDemandBase createDemand()
	{
		return new PaymentDemand();
	}

	protected void fillDemand(PaymentDemandBase demand, GateDocument document) throws GateLogicException, GateException
	{
		if (!(document instanceof ClientAccountsTransfer))
			throw new GateLogicException("Неверный тип платежа, должен быть - ClientAccountsTransfer.");
		ClientAccountsTransfer clientAccountsTransfer = (ClientAccountsTransfer) document;
/*
заполняем поля
	Account, ClientCode, DocumentDate, IsResident,
	CurrencyCode, IsCur, SummaOut,
	OperationType, OperationKind,
*/
		super.fillDemand(demand, clientAccountsTransfer);

		demand.setReceiverAccount(clientAccountsTransfer.getReceiverAccount());
		//цитата из Интеграция с RS-Retail Pervasive:Код валюты счета получателя	Psdepdoc.dbt	CodeCur_Receiver		=$(CodeCurReceiver)	Равен Code_Currency
		demand.setReceiverCurrencyCode(demand.getCurrencyCode());
//todo вынести Ground в gate-documents-config.xml
		demand.setGround("п/п по заявл. Кл-та от "+ DateHelper.toString(demand.getDocumentDate())+" адрес. Перевод собственных средств Без налога НДС");

		AccountImpl account = getAccount(demand.getReceiverAccount(), demand.getClientCode());
		demand.setReceiverOffice( account.getBranch() );
		demand.setReceiverCorAccount(account.getDescription());
	}
}
