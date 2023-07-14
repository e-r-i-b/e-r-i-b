package com.rssl.phizic.mbk;

import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.messaging.MessageCoordinator;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class MBKP2PMessageCoordinator extends MessageCoordinator
{
	private final ErmbAuxChannel auxModule = moduleManager.getModule(ErmbAuxChannel.class);

	/**
	 * ctor
	 */
	public MBKP2PMessageCoordinator()
	{
		registerProcessor(P2PRequest.class, auxModule.getMBKP2PProcessor());
	}

}
