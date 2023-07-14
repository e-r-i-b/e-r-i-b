package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AbstractRUSPayment;
import com.rssl.phizic.gate.payments.AccountJurTransfer;

/**
 * @author krenev
 * @ created 14.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class IntraBankSender extends AbstractGatePaymentSender
{
	public IntraBankSender(GateFactory factory)
	{
		super(factory);
	}

	protected ResidentBank getReceiverBank(AbstractTransfer payment) throws GateException
	{
		if (!(payment instanceof AbstractRUSPayment))
			throw new GateException("Некорректный тип платежа. Ожидался AbstractRUSPayment.");

		AbstractRUSPayment rusPayment = (AbstractRUSPayment) payment;
		return rusPayment.getReceiverBank();
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountJurTransfer)){
			throw new GateException("Неверный тип платежа, должен быть - AccountJurTransfer.");
		}
		AccountJurTransfer accountJurTransfer = (AccountJurTransfer) document;
		Currency chargeOffcurrency= getAccountCurrency(accountJurTransfer.getChargeOffAccount());
		Currency receiverAccountCurrency= getAccountCurrency(accountJurTransfer.getReceiverAccount());
		if (!chargeOffcurrency.compare(receiverAccountCurrency))
		{
			throw new GateLogicException("Вы не можете совершить перевод в разных валютах. Выберите счета в одной валюте");
		}
	}
}
