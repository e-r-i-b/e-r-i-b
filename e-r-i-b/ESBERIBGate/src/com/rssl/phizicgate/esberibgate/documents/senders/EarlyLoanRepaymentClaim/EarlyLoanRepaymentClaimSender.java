package com.rssl.phizicgate.esberibgate.documents.senders.EarlyLoanRepaymentClaim;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizicgate.esberibgate.ws.jms.common.document.AbstractOfflineJMSDocumentSender;

/**
 * отправка сообщения на досрочное погашение кредитов
 * @author basharin
 * @ created 16.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EarlyLoanRepaymentClaimSender extends AbstractOfflineJMSDocumentSender
{
	public static final String CODE_EKP = "EKP";

	/**
	 * конструктор
	 * @param factory фабрика
	 */
	public EarlyLoanRepaymentClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public final void send(GateDocument document) throws GateException, GateLogicException
	{
		EarlyLoanRepaymentClaim doc = (EarlyLoanRepaymentClaim) document;
		doc.setOperUID(OperationContext.getCurrentOperUID());
		doc.setExternalId(new RandomGUID().getStringValue());
		process(new EarlyLoanRepaymentClaimProcessor(getFactory(), doc));
	}

	public void repeatSend(GateDocument document) throws GateException, GateLogicException
	{
		process(new EarlyLoanRepaymentClaimProcessor(getFactory(), (EarlyLoanRepaymentClaim) document));
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
