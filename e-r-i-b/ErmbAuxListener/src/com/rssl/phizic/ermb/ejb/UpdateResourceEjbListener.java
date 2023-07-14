package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;

/**
 * Слушатель оповещений об изменении продукта клиента в ЕРМБ
 * @author Rtischeva
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateResourceEjbListener extends EjbListenerBase
{
	private final ErmbAuxChannel ermbAuxChannel = moduleManager.getModule(ErmbAuxChannel.class);

	protected ErmbAuxChannel getModule()
	{
		return ermbAuxChannel;
	}

	protected MessageProcessor getMessageProcessor()
	{
		return ermbAuxChannel.getUpdateResourceProcessor();
	}
}
