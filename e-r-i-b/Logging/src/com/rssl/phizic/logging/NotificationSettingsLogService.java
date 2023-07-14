package com.rssl.phizic.logging;

import com.rssl.phizic.logging.push.*;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;

import java.util.Calendar;

/**
 * сервис для записи логов в БД об изменении настроек оповещений
 * @author basharin
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSettingsLogService
{
	private static final String PUSH_EVENT_STATISTIC_ERROR = "Не удалось записать информацию о добавлении push устройства в таблицу статистики";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static volatile PushMailStatisticLogWriter logWriter = null;

	/**
	 * Сохранияем запись в лог о изменении статуса устройства
	 * @param creationDate - время измениния статуса устройства
	 * @param clientId - зашифрованный индетификатор клиента
	 * @param mguidHash  - зашифрованный индетификатор устройства
	 * @param type  - статус устройства
	 */
	public void savePushQueueDeviceToLog(Calendar creationDate, String clientId, String mguidHash, PushDeviceStatus type)
	{
		PushQueueDeviceLogEntry logEntry = new PushQueueDeviceLogEntry();
		logEntry.setCreationDate(creationDate);
		logEntry.setClientId(clientId);
		logEntry.setMguidHash(mguidHash);
		logEntry.setType(type);
		try
		{
			getMessageLogWriter().writeToPushWriter(logEntry);
		}
		catch (Exception e)
		{
			log.error(PUSH_EVENT_STATISTIC_ERROR);
		}
	}

	private PushMailStatisticLogWriter getMessageLogWriter()
	{
		if (logWriter == null)
		{
			synchronized (NotificationSettingsLogService.class)
			{
				if (logWriter == null)
				{
					logWriter = new PushMailStatisticLogWriter();
				}
			}
		}
		return logWriter;
	}
}