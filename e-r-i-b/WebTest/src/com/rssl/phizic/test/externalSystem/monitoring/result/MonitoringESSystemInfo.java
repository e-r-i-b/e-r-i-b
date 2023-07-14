package com.rssl.phizic.test.externalSystem.monitoring.result;

import com.rssl.phizic.logging.messaging.System;

import java.util.List;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по системе
 */

public class MonitoringESSystemInfo
{
	private final System system;
	private final List<MonitoringESRequestInfo> requests;
	private final long count;
	private long[] countByApplication;

	/**
	 * конструктор
	 * @param system система
	 * @param requests информация в разрезе запросов
	 * @param count общее количество запросов
	 * @param countByApplication количество запросов в разрезе системы инициирующей запрос
	 */
	public MonitoringESSystemInfo(System system, List<MonitoringESRequestInfo> requests, long count, long[] countByApplication)
	{
		this.system = system;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.requests = requests;
		this.count = count;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.countByApplication = countByApplication;
	}

	/**
	 * @return система
	 */
	public System getSystem()
	{
		return system;
	}

	/**
	 * @return информация в разрезе запросов
	 */
	public List<MonitoringESRequestInfo> getRequests()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return requests;
	}

	/**
	 * @return общее количество запросов
	 */
	public long getCount()
	{
		return count;
	}

	/**
	 * @return количество запросов в разрезе системы инициирующей запрос
	 */
	public long[] getCountByApplication()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return countByApplication;
	}
}
