package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.business.ermb.messaging.ErmbTransportChannel;
import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.synchronization.types.*;

/**
 * @author Rtischeva
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */
public class ErmbMessageCoordinator extends MessageCoordinator
{
	private final ErmbSmsChannel smsModule = moduleManager.getModule(ErmbSmsChannel.class);
	private final ErmbAuxChannel auxModule = moduleManager.getModule(ErmbAuxChannel.class);
	private final ErmbTransportChannel transportModule = moduleManager.getModule(ErmbTransportChannel.class);

	/**
	 * ctor
	 */
	public ErmbMessageCoordinator()
	{
		registerProcessor(SMSRq.class, smsModule.getSmsProcessor());

		registerProcessor(UpdateClientRq.class, auxModule.getUpdateClientProcessor());

		registerProcessor(UpdateResourceRq.class, auxModule.getUpdateResourceProcessor());

		registerProcessor(ConfirmProfilesRq.class, auxModule.getConfirmProfileProcessor());

		registerProcessor(ServiceFeeResultRq.class, transportModule.getServiceFeeResultRqProcessor());

		registerProcessor(CheckImsiResponse.class, transportModule.getTransportSmsResponseProcessor());
	}

}
