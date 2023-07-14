package com.rssl.phizic.test.externalSystem.monitoring.result;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.test.externalSystem.monitoring.MonitoringESRequestCollector;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 25.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Ѕилдер данных csv
 */

public class MonitoringESRequestInfoCSVBuilder
{
	private static final String DELIMITER = ";";
	private static final String STRING_DELIMITER = "\n";

	/**
	 * @return собрать CSV данные
	 */
	public static byte[] build()
	{
		StringBuilder builder = new StringBuilder();
		MonitoringESResult monitoringESResult = MonitoringESRequestCollector.getInstance().buildInfo();
		addHeader(builder, monitoringESResult.getApplications());
		for (MonitoringESSystemInfo systemInfo : monitoringESResult.getSystemInformationList())
		{
			if (systemInfo.getCount() == 0)
				continue;

			addRow(builder, systemInfo.getSystem(), String.valueOf(systemInfo.getRequests().size()), null, systemInfo.getCount(), systemInfo.getCountByApplication());
			for (MonitoringESRequestInfo requestInfo : systemInfo.getRequests())
			{
				addRow(builder, systemInfo.getSystem(), requestInfo.getMessageType(), requestInfo.getAvgTime(), requestInfo.getCount(), requestInfo.getCountByApplication());
			}
		}

		return builder.toString().getBytes();
	}

	private static void addHeader(StringBuilder builder, Application[] applications)
	{
		addCell(builder, "—истема");
		addCell(builder, "“ип сообщени€");
		addCell(builder, "—реднее врем€ обработки");
		addCell(builder, "ќбщее количество запросов");
		for (int i = 0; i < applications.length; i++)
			addCell(builder, applications[i]);
		builder.append(STRING_DELIMITER);
	}

	@SuppressWarnings("MethodWithTooManyParameters")
	private static void addRow(StringBuilder builder, System system, String messageType, Long avgTime, long count, long[] countByApplication)
	{
		addCell(builder, system.name());
		addCell(builder, StringHelper.isEmpty(messageType)? "Ч": messageType);
		addCell(builder, avgTime == null? "Ч": avgTime);
		addCell(builder, count);
		for (int i = 0; i < countByApplication.length; i++)
			addCell(builder, countByApplication[i]);
		builder.append(STRING_DELIMITER);
	}


	private static void addCell(StringBuilder builder, Object value)
	{
		builder.append(StringHelper.getEmptyIfNull(value)).append(DELIMITER);
	}

}
