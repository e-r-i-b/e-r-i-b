package com.rssl.phizgate.basket;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.writers.MessageLogWriter;

import java.util.Calendar;

/**
 * @author vagin
 * @ created 19.06.14
 * @ $Author$
 * @ $Revision$
 * Хелпер логирования входящих сообщений от АС "AutoPay" в рамах корзины платежей.
 */
public class BasketProxyLoggerHelper
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Запись в лог сообщения
	 * @param request - тело сообщения
	 * @param rqUID - уникальный идентифиатор сообщения
	 * @param messageType - тип сообщения.
	 */
	public static void writeToLog(String request, String rqUID, String messageType)
	{
		try
		{
			MessagingLogEntry logEntry = MessageLogService.createLogEntry();

			logEntry.setApplication(ApplicationInfo.getCurrentApplication());
			logEntry.setSystem(com.rssl.phizic.logging.messaging.System.esberib);
			logEntry.setDate(Calendar.getInstance());
			logEntry.setMessageRequestId(rqUID);
			logEntry.setMessageType(messageType);
			logEntry.setMessageRequest(request);

			MessageLogWriter writer = MessageLogService.getMessageLogWriter();
			writer.write(logEntry);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
