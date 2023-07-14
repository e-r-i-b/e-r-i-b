package com.rssl.phizic.config.build;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author gladishev
 * @ created 23.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringConfig extends Config
{
	private static final String SERVER_IDLE_INTERVAL_TIMEOUT = "com.rssl.iccs.monitoring.server.available.idle.timeout.interval";
	private static final String SERVER_PING_TIMEOUT = "com.rssl.iccs.monitoring.server.available.timeout.ping";
	private static final String PAGE_URL = "com.rssl.iccs.monitoring.server.available.page.url";
	private static final String REPORT_PERIODS_COUNT_MONTH = "com.rssl.iccs.monitoring.system.available.report.periods.count.month";
	private static final String REPORT_PERIODS_COUNT_WEEK  = "com.rssl.iccs.monitoring.system.available.report.periods.count.week";

	private static final String MONITORING_SERVICES_LIST_KEY = "com.rssl.iccs.monitoring.services";
	private static final String MONITORING_SERVICE_KEY_PREFIX = "com.rssl.iccs.monitoring.service.";
	private static final String MONITORING_SERVICE_NAME_SUFFIX = ".serviceName";
	private static final String REQUEST_TAGS_SUFFIX = ".reguestTags";
	private static final String RESPONCE_TAGS_SUFFIX = ".responceTags";
	private static final String MONITORUNG_ERROR_KODES_KEY = "com.rssl.iccs.monitoring.service.errorCodes";
	private static final Pattern MONITORUNG_VALUES_DELIMITER_PATTERN = Pattern.compile(";");
	private static final String USE_PERCENT_COUNTER = "com.rssl.iccs.monitoring.usePercentCounter";
	private static final String USE_REAL_TIME_COUNTER = "com.rssl.iccs.monitoring.useRealTimeCounter";

	private int serverIdleIntervalTimeout;
	private int serverPingTimeout;
	private String pageURL;
	private int sysIdleReportPeriodsCountByMonth;
	private int sysIdleReportPeriodsCountByWeek;
	private boolean usePercentCounter;
	private boolean useRealTimeCounter;

	private final Map<String, String> requestTagConfiguration = new HashMap<String, String>();
	private final Map<String, String> responceTagConfiguration = new HashMap<String, String>();
	private final List<String> errorCodes = new ArrayList<String>();

	public MonitoringConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return интервал, простой дольше которого считается как время простоя системы
	 */
	public int getServerIdleIntervalTimeout()
	{
		return serverIdleIntervalTimeout;
	}

	/**
	 * @return таймаут пинга страницы мониторинга
	 */
	public int getServerPingTimeout()
	{
		return serverPingTimeout;
	}

	/**
	 * @return URL тестовой страницы мониторинга
	 */
	public String getPageURL()
	{
		return pageURL;
	}

	/**
	 * @return количество частей разбития отчета о времени
	 * доступности системы (для отчета за месяц)
	 */
	public int getSysIdleReportPeriodsCountByMonth()
	{
		return sysIdleReportPeriodsCountByMonth;
	}

	/**
	 * @return количество частей разбития отчета о времени
	 * доступности системы (для отчета за неделю)
	 */
	public int getSysIdleReportPeriodsCountByWeek()
	{
		return sysIdleReportPeriodsCountByWeek;
	}

	public List<String> getErrorCodes()
	{
		return Collections.unmodifiableList(errorCodes);
	}

	public Map<String, String> getRequestTagConfiguration()
	{
		return Collections.unmodifiableMap(requestTagConfiguration);
	}

	public Map<String, String> getResponceTagConfiguration()
	{
		return Collections.unmodifiableMap(responceTagConfiguration);
	}

	public boolean isUsePercentCounter()
	{
		return usePercentCounter;
	}

	public boolean isUseRealTimeCounter()
	{
		return useRealTimeCounter;
	}

	public void doRefresh() throws ConfigurationException
	{
		try
		{
			serverIdleIntervalTimeout = getIntProperty(SERVER_IDLE_INTERVAL_TIMEOUT);
			serverPingTimeout = getIntProperty(SERVER_PING_TIMEOUT);
			sysIdleReportPeriodsCountByMonth = getIntProperty(REPORT_PERIODS_COUNT_MONTH);
			sysIdleReportPeriodsCountByWeek = getIntProperty(REPORT_PERIODS_COUNT_WEEK);
			pageURL = getProperty(PAGE_URL);

			String[] services = MONITORUNG_VALUES_DELIMITER_PATTERN.split(getProperty(MONITORING_SERVICES_LIST_KEY));
			for(String service: services)
			{
				String serviceParametersPrafix = MONITORING_SERVICE_KEY_PREFIX.concat(service);
				String serviceName = getProperty(serviceParametersPrafix.concat(MONITORING_SERVICE_NAME_SUFFIX));
				String[] requestTags = MONITORUNG_VALUES_DELIMITER_PATTERN.split(getProperty(serviceParametersPrafix.concat(REQUEST_TAGS_SUFFIX)));
				String[] responceTags = MONITORUNG_VALUES_DELIMITER_PATTERN.split(getProperty(serviceParametersPrafix.concat(RESPONCE_TAGS_SUFFIX)));

				for(String requestTag: requestTags)
					requestTagConfiguration.put(requestTag,serviceName);
				for(String responceTag: responceTags)
					responceTagConfiguration.put(responceTag,serviceName);
			}

			String[] errorCodesArray = MONITORUNG_VALUES_DELIMITER_PATTERN.split(getProperty(MONITORUNG_ERROR_KODES_KEY));
			errorCodes.addAll(Arrays.asList(errorCodesArray));
			usePercentCounter = getBoolProperty(USE_PERCENT_COUNTER);
			useRealTimeCounter = getBoolProperty(USE_REAL_TIME_COUNTER);
		}
		catch (NumberFormatException e)
		{
			throw new ConfigurationException("Ошибка при обновлении параметров мониторинга", e);
		}
	}
}
