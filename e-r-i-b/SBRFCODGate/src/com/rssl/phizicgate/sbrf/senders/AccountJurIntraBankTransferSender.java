package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;

/**
 * @author gladishev
 * @ created 09.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountJurIntraBankTransferSender extends JurPaymentSender
{
	public AccountJurIntraBankTransferSender(GateFactory factory)
	{
		super(factory);
	}

	protected String getUsePaymentOrder()
	{
		return ConfigFactory.getConfig(SpecificGateConfig.class).getUsePaymentOrderForAccountJurIntrabankTransfer();
	}
}
