package com.rssl.phizic.test.externalSystem.monitoring.result;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * информация по конкретному тпиу запроса
 */

public class MonitoringESRequestInfo
{
	private final String messageType;
	private final long count;
	private final long avgTime;
	private final long[] countByApplication;

	/**
	 * конструктор
	 * @param messageType тип запроса
	 * @param avgTime реднее время выполнения
	 * @param count количество запросов
	 * @param countByApplication количество запросов в разрезе системы инициирующей запрос
	 */
	public MonitoringESRequestInfo(String messageType, long avgTime, long count, long[] countByApplication)
	{
		this.messageType = messageType;
		this.count = count;
		this.avgTime = avgTime;
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.countByApplication = countByApplication;
	}

	/**
	 * @return тип запроса
	 */
	public String getMessageType()
	{
		return messageType;
	}

	/**
	 * @return среднее время выполнения
	 */
	public long getAvgTime()
	{
		return avgTime;
	}

	/**
	 * @return количество запросов
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
