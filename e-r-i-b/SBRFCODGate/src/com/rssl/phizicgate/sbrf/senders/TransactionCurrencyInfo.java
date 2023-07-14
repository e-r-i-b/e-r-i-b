package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.sbrf.bankroll.RequestHelper;

/**
 * Информация о валютах платежа
 * @author gladishev
 * @ created 23.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class TransactionCurrencyInfo
{
	private Currency chargeOffAccountCurrency;
	private Currency destinationAccountCurrency;

	public TransactionCurrencyInfo(GateFactory factory, GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof AbstractAccountTransfer))
			throw new GateException("Неверный тип документа. Ожидается AbstractAccountTransfer");

		AbstractAccountTransfer payment = (AbstractAccountTransfer) gateDocument;
		chargeOffAccountCurrency = AbstractDocumentSenderBase.getAccountCurrency(payment.getChargeOffAccount());
		destinationAccountCurrency = AbstractDocumentSenderBase.getAccountCurrency(new RequestHelper(factory).getReceiverAccount(payment));
	}

	public Currency getChargeOffAccountCurrency()
	{
		return chargeOffAccountCurrency;
	}

	public Currency getDestinationAccountCurrency()
	{
		return destinationAccountCurrency;
	}

	public boolean isConversion()
	{
		return !destinationAccountCurrency.compare(chargeOffAccountCurrency);
	}
}
