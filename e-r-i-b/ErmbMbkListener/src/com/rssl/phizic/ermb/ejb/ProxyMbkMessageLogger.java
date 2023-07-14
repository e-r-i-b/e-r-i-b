package com.rssl.phizic.ermb.ejb;

import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;

/**
 * Логирование сообщений прокси мбк -> блок
 * @author Puzikov
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 */

class ProxyMbkMessageLogger
{
	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	void write(MBKRegistration message)
	{
		try
		{
			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			MessagingLogEntry entry = MessageLogService.createLogEntry();

			entry.setApplication(ApplicationInfo.getCurrentApplication());
			entry.setSystem(System.ProxyMbk);
			entry.setMessageRequest(message.toString());
			entry.setMessageRequestId(String.valueOf(message.getId()));
			entry.setMessageType("ProxyMbk");

			writer.write(entry);
		}
		catch (Exception e)
		{
			log.error("Ошибка записи в журнал сообщений. " + message, e);
		}
	}
}
