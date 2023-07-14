package com.rssl.phizicgate.sbrf.senders.depocod;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;

/**
 * —ендер перевода физическому лицу в другой банк через платежную систему –оссии
 *
 * @author gladishev
 * @ created 21.04.2014
 * @ $Author$
 * @ $Revision$
 */

public class DepoCodRUSPaymentSender extends DepoCodIntraBankSender
{
	public DepoCodRUSPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	@Override
	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return ACC2ACC365_REQUEST;
	}

	@Override
	protected void fillOurBankMessage(GateMessage gateMessage, AbstractAccountTransfer payment) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
