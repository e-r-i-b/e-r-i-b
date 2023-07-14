package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.business.ermb.sms.messaging.ErmbSmsChannel;
import com.rssl.phizic.ejb.EjbListenerBase;
import com.rssl.phizic.messaging.MessageProcessor;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Слушатель СМС-канала
 * Принимает СМС-запросы клиентов.
 */
public class SmsEjbListener extends EjbListenerBase
{
	private final ErmbSmsChannel ermbSmsChannel = moduleManager.getModule(ErmbSmsChannel.class);

	protected Module getModule()
	{
		return ermbSmsChannel;
	}

	protected MessageProcessor getMessageProcessor()
	{
		return ermbSmsChannel.getSmsProcessor();
	}
}
