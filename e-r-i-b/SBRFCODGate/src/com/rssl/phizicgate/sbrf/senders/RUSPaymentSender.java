package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * @author Egorova
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */
public class RUSPaymentSender extends IntraBankSender
{
	public RUSPaymentSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException
	{
		return "transferOtherBank_q"; //всегда перевод в другой банк
	}
}
