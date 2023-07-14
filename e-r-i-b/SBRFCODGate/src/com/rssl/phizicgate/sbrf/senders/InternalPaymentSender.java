package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizicgate.sbrf.bankroll.RequestHelper;

/**
 * @author Egorova
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class InternalPaymentSender extends ConvertionDocumentSenderBase
{
	public InternalPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected ResidentBank getReceiverBank(AbstractTransfer payment)
	{
		return null; //перевод между счетами всегда в рамках 1 банка.
	}
}
