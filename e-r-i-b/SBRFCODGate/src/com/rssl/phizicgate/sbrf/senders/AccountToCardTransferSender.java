package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizicgate.sbrf.bankroll.RequestHelper;

/**
 * @author krenev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 * Сендер перевода счет-карта через карточный счет.
 */
public class AccountToCardTransferSender extends InternalPaymentSender
{
	public AccountToCardTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountToCardTransfer))
		{
			throw new GateException("Неверный тип документа. Ожидается AccountToCardTransfer");
		}
		try
		{
			//пытаемся получить КС.
			getReceiverAccount((AccountToCardTransfer) document);
		}
		catch (GateException e)
		{
			throw new GateLogicException("Для данной карты операция пополнения невозможна", e);
		}
	}
}
