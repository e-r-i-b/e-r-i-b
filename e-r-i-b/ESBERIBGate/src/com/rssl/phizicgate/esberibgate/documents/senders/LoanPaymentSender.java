package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.LoanTransfer;
import com.rssl.phizicgate.esberibgate.documents.LoanPaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.CardAcctId_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.OperName_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentSender extends PaymentSenderBase
{
	public LoanPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof LoanTransfer))
		{
			throw new GateException("Неверный тип документа, должен быть - LoanTransfer.");
		}
		LoanPaymentsRequestHelper paymentsRequestHelper = new LoanPaymentsRequestHelper(getFactory());
		LoanTransfer transfer = (LoanTransfer) document;
		XferInfo_Type xferInfo = new XferInfo_Type();

		Client owner = getBusinessOwner(transfer);
		xferInfo.setCustInfo(paymentsRequestHelper.createCustInfo(owner));
		Card card = getCard(owner, transfer.getChargeOffCard(), transfer.getOffice());
		CardAcctId_Type cardAcctId = paymentsRequestHelper.createCardAcctId(card, owner, transfer.getChargeOffCardExpireDate());
		xferInfo.setCardAcctIdFrom(cardAcctId);
		xferInfo.setLoanAcctIdTo(createLoanAcctId(transfer.getLoanExternalId(),
										transfer.getAccountNumber(), transfer.getAgreementNumber()));
		if (!isLongOfferMode())
		{
			//параметры разбивки заполняем если не ДП.
			xferInfo.setIdSpacing(transfer.getIdSpacing());
			xferInfo.setDateCalc(LoanPaymentsRequestHelper.getStringDate(transfer.getSpacingDate()));
		}
		Money amount = transfer.getChargeOffAmount();
		if (amount == null && isLongOfferMode())
		{   //передаем "некую фиктивную сумму" BUG036755
			xferInfo.setCurAmt(BigDecimal.ONE);
			xferInfo.setAcctCur("RUB");
		}
		else
		{
			xferInfo.setCurAmt(amount.getDecimal());
			xferInfo.setAcctCur(amount.getCurrency().getCode());
		}
		return xferInfo;
	}

	protected OperName_Type getOperationName(AbstractTransfer document)
	{
		return OperName_Type.TCC;
	}
}