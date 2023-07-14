package com.rssl.phizicgate.esberibgate.documents.senders.EarlyLoanRepaymentClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.loan.CancelEarlyLoanRepaymentClaim;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOfflineJMSDocumentSender;

/**
 * отправка сообщения на досрочное погашение кредитов
 * @author basharin
 * @ created 06.07.15
 * @ $Author$
 * @ $Revision$
 */

public class CancelEarlyLoanRepaymentClaimSender extends AbstractOfflineJMSDocumentSender
{
	/**
	 * конструктор
	 * @param factory фабрика
	 */
	public CancelEarlyLoanRepaymentClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		CancelEarlyLoanRepaymentClaim cancelEarlyLoanRepaymentClaim = (CancelEarlyLoanRepaymentClaim) document;
		cancelEarlyLoanRepaymentClaim.setExternalId(new RandomGUID().getStringValue());
		process(new CancelEarlyLoanRepaymentClaimProcessor(getFactory(), cancelEarlyLoanRepaymentClaim, (EarlyLoanRepaymentClaim) cancelEarlyLoanRepaymentClaim.getTransferPayment()));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Отзыв завяки на изменение параметров подписки по карте не поддерживается");
	}

	public void rollback(WithdrawDocument document)
	{
		throw new UnsupportedOperationException("Отзыв завяки на изменение параметров подписки по карте не поддерживается");
	}

	public void prepare(GateDocument document)
	{
		throw new UnsupportedOperationException("Подготовка завяки на изменение параметров подписки по карте не поддерживается");
	}

	public void confirm(GateDocument document)
	{
		throw new UnsupportedOperationException("Подтверждение завяки на изменение параметров подписки по карте не поддерживается");
	}

	public void validate(GateDocument document)
	{
		throw new UnsupportedOperationException("Валидация завяки на изменение параметров подписки по карте не поддерживается");
	}
}
