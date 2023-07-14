package com.rssl.phizic.logging;

import com.rssl.phizic.logging.push.*;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.logging.Log;

import java.util.Calendar;

/**
 * ������ ��� ������ ����� � �� �� ��������� �������� ����������
 * @author basharin
 * @ created 31.10.13
 * @ $Author$
 * @ $Revision$
 */

public class NotificationSettingsLogService
{
	private static final String PUSH_EVENT_STATISTIC_ERROR = "�� ������� �������� ���������� � ���������� push ���������� � ������� ����������";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static volatile PushMailStatisticLogWriter logWriter = null;

	/**
	 * ���������� ������ � ��� � ��������� ������� ����������
	 * @param creationDate - ����� ��������� ������� ����������
	 * @param clientId - ������������� ������������� �������
	 * @param mguidHash  - ������������� ������������� ����������
	 * @param type  - ������ ����������
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