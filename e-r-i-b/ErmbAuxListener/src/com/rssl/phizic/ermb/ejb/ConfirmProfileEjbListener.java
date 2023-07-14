package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.business.ermb.auxiliary.ErmbAuxChannel;
import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;

/**
 @author: EgorovaA
 @ created: 29.01.2013
 @ $Author$
 @ $Revision$
 */

/**
 * Принимает сообщения с подтверждениями изменения профилей ЕРМБ
 */
public class ConfirmProfileEjbListener extends EjbListenerBase
{
	private final ErmbAuxChannel ermbAuxChannel = moduleManager.getModule(ErmbAuxChannel.class);

	protected Module getModule()
	{
		return ermbAuxChannel;
	}

	protected MessageProcessor getMessageProcessor()
	{
		return ermbAuxChannel.getConfirmProfileProcessor();
	}
}
