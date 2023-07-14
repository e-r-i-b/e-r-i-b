package com.rssl.phizicgate.sbrf.senders.depocod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * Сендер перевода со счета на карту
 *
 * @author gladishev
 * @ created 22.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodAccountToCardTransferSender extends DepoCodSenderBase
{
	public DepoCodAccountToCardTransferSender(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return isSameTB(gateDocument) ? ACC2ACC_REQUEST : ACC2ACC143_REQUEST;
	}

	@Override
	protected void fillExternalBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
