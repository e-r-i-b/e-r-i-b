package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;

/**
 * Слушатель оповещений об изменении данных клиента в ЕРМБ
 * @author Rtischeva
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateClientEjbListener extends EjbListenerBase
{
	private final ErmbAuxChannel ermbAuxChannel = moduleManager.getModule(ErmbAuxChannel.class);

	protected Module getModule()
	{
		return ermbAuxChannel;
	}

	protected MessageProcessor getMessageProcessor()
	{
		return ermbAuxChannel.getUpdateClientProcessor();
	}
}
