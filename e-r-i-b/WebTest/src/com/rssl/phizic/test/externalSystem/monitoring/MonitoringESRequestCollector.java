package com.rssl.phizic.test.externalSystem.monitoring;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESRequestInfo;
import com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESResult;
import com.rssl.phizic.test.externalSystem.monitoring.result.MonitoringESSystemInfo;

import java.util.*;

/**
 * @author akrenev
 * @ created 24.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Сборщик информации
 */

public final class MonitoringESRequestCollector
{
	private static final MonitoringESRequestCollector COLLECTOR = new MonitoringESRequestCollector();

	private final Set<Application> applications = new HashSet<Application>();
	private final Map<System, Map<String, MonitoringESRequestInfoCollector>> info = getEmptyInfoContainer();
	private volatile State state = State.stopped;

	private MonitoringESRequestCollector(){}

	/**
	 * @return инстанс сборщика информации
	 */
	public static MonitoringESRequestCollector getInstance()
	{
		return COLLECTOR;
	}

	/**
	 * проинициализировать
	 */
	public synchronized void init()
	{
		applications.clear();
		info.clear();
		info.putAll(getEmptyInfoContainer());
	}

	/**
	 * запустить сбор информации
	 */
	public synchronized void start()
	{
		state = State.processed;
	}

	/**
	 * добавить информацию
	 * @param source источник данных
	 */
	public synchronized void add(MessagingLogEntry source)
	{
		if (state != State.processed)
			return;

		applications.add(source.getApplication());

		String messageType = source.getMessageType();
		Map<String, MonitoringESRequestInfoCollector> systemInfo = info.get(source.getSystem());
		MonitoringESRequestInfoCollector requestInfo = systemInfo.get(messageType);
		if (requestInfo == null)
		{
			requestInfo = new MonitoringESRequestInfoCollector();
			systemInfo.put(messageType, requestInfo);
		}
		requestInfo.add(source.getExecutionTime(), source.getApplication());
	}

	/**
	 * остановить сбор информации
	 */
	public synchronized void stop()
	{
		state = State.stopped;
	}

	/**
	 * @return собрать текущую инфу
	 */
	public synchronized MonitoringESResult buildInfo()
	{
		Application[] existsApplications = applications.toArray(new Application[applications.size()]);
		List<MonitoringESSystemInfo> information = new ArrayList<MonitoringESSystemInfo>();
		for (Map.Entry<System, Map<String, MonitoringESRequestInfoCollector>> systemListEntry : info.entrySet())
		{
			long systemRequestsCount = 0;
			List<MonitoringESRequestInfo> requests = new ArrayList<MonitoringESRequestInfo>();
			long[] countByApplicationForSystem = new long[existsApplications.length];
			for (Map.Entry<String, MonitoringESRequestInfoCollector> requestInfo : systemListEntry.getValue().entrySet())
			{
				MonitoringESRequestInfoCollector requestInfoValue = requestInfo.getValue();
				long requestTypeCount = requestInfoValue.getCount();
				systemRequestsCount += requestTypeCount;
				requests.add(new MonitoringESRequestInfo(requestInfo.getKey(), requestInfoValue.getAvgTime(), requestTypeCount, requestInfoValue.getCountAndUpdateCollection(existsApplications, countByApplicationForSystem)));
			}
			information.add(new MonitoringESSystemInfo(systemListEntry.getKey(), requests, systemRequestsCount, countByApplicationForSystem));
		}
		return new MonitoringESResult(existsApplications, information);
	}

	/**
	 * @return состояние
	 */
	public synchronized State getState()
	{
		return state;
	}

	private Map<System, Map<String, MonitoringESRequestInfoCollector>> getEmptyInfoContainer()
	{
		Map<System, Map<String, MonitoringESRequestInfoCollector>> container = new HashMap<System, Map<String, MonitoringESRequestInfoCollector>>();
		for (System system : System.values())
			container.put(system, new HashMap<String, MonitoringESRequestInfoCollector>());

		return container;
	}

	@SuppressWarnings("PublicInnerClass")
	public enum State {processed, stopped}

	private class MonitoringESRequestInfoCollector
	{
		private final Map<Application, Long> counters;
		private long sumTime = 0;
		private long counter = 0;

		private MonitoringESRequestInfoCollector()
		{
			counters = new HashMap<Application, Long>(Application.values().length);
			for (Application application : Application.values())
				counters.put(application, 0L);
		}

		public void add(Long executionTime, Application application)
		{
			counters.put(application, counters.get(application) + 1);
			counter++;
			sumTime += executionTime == null? 0: executionTime;
		}

		public long getCount()
		{
			return counter;
		}

		public long[] getCountAndUpdateCollection(Application[] application, long[] countByApplicationForSystem)
		{
			long[] countByApplication = new long[application.length];
			for (int i = 0; i < application.length; i++)
			{
				Long count = counters.get(application[i]);
				countByApplication[i] = count;
				countByApplicationForSystem[i] += count;
			}
			return countByApplication;
		}

		public long getAvgTime()
		{
			return sumTime / counter;
		}
	}
}
