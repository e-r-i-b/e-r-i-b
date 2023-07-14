package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;

/**
 * Слушатель результата списания абонентской платы
 * @author Puzikov
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ServiceFeeResultRqListener extends EjbListenerBase
{
	private final ErmbTransportChannel ermbTransportChannel = moduleManager.getModule(ErmbTransportChannel.class);

	@Override
	protected Module getModule()
	{
		return ermbTransportChannel;
	}

	@Override
	protected MessageProcessor getMessageProcessor()
	{
		return ermbTransportChannel.getServiceFeeResultRqProcessor();
	}
}
