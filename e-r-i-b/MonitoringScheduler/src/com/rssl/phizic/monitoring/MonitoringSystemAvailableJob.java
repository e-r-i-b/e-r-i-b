package com.rssl.phizic.monitoring;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.monitoring.serveravailability.IdleType;
import com.rssl.phizic.business.monitoring.serveravailability.MonitoringServerAvailabilityService;
import com.rssl.phizic.business.monitoring.serveravailability.ServerCommonWorkTime;
import com.rssl.phizic.business.monitoring.serveravailability.ServerIdleTime;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.config.build.MonitoringConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;

/**
 * Джоб мониторинга времени доступности системы
 * @author gladishev
 * @ created 23.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringSystemAvailableJob extends MonitoringJobBase
{
	private static final MonitoringServerAvailabilityService monitoringService = new MonitoringServerAvailabilityService();
	private static final int RESPONSE_CODE_OK = 200;

	private static final Object PING_PAGE_LOCKER = new Object();
	private static volatile String pingPage = null;

	public MonitoringSystemAvailableJob() throws JobExecutionException
	{
		super();
	}

	public void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			MonitoringConfig config = ConfigFactory.getConfig(MonitoringConfig.class);
			int pingTimeoutMillis = config.getServerPingTimeout(); //таймаут пинга страницы мониторинга
			int minIdleIntervalTimeout = config.getServerIdleIntervalTimeout(); //минимально разрешимое время простоя (в милисекундах)

			Calendar currentTime = Calendar.getInstance();

			boolean needPing = true;
			ServerCommonWorkTime serverWorkTime = monitoringService.getServerAvailableTime(getAppServerName());
			if (serverWorkTime == null)
			{
				serverWorkTime = new ServerCommonWorkTime();
				serverWorkTime.setServerId(getAppServerName());
				serverWorkTime.setStartDate(currentTime);
			}
			else
			{
				Calendar serverLastActiveDate = serverWorkTime.getEndDate();
				if (DateHelper.diff(currentTime, serverLastActiveDate) > minIdleIntervalTimeout) //=>в период [serverLastActiveDate-currentTime] не работал джоб
				{
					//сначала проверяем не было ли простоя приложения до начала периода простоя джоба
					//время последнего пинга
					Calendar serverLastPingDate = serverWorkTime.getEndPingDate() != null ? serverWorkTime.getEndPingDate()
																							: serverWorkTime.getStartDate();
					if (DateHelper.diff(serverLastActiveDate, serverLastPingDate)>minIdleIntervalTimeout)
					{
						monitoringService.addOrUpdateServerIdleTime(
								new ServerIdleTime(getAppServerName(), serverLastActiveDate, currentTime, IdleType.fullIdle));
					}

					//добавляем запись о простое джоба
					monitoringService.addOrUpdateServerIdleTime(
							new ServerIdleTime(getAppServerName(), serverLastActiveDate, currentTime, IdleType.jobIdle));
					
					//начинаем отчет времени простоя системы с текущего момента
					serverWorkTime.setEndPingDate(currentTime);
					needPing = false;
				}
			}	

			//пингуем тестовую страницу
			if (needPing && pingPage(pingTimeoutMillis, getPingPage())) //т.е. приложение в данный момент работает
			{
				//время последнего пинга
				Calendar serverLastPingDate = serverWorkTime.getEndPingDate() != null ? serverWorkTime.getEndPingDate() 
																						: serverWorkTime.getStartDate();
				long currentInterval; //текущее время простоя (в милисекундах)
				if (serverLastPingDate == null)
					currentInterval = DateHelper.diff(currentTime, serverWorkTime.getStartDate());
				else
					currentInterval = DateHelper.diff(currentTime, serverLastPingDate);

				//проверяем, что время последней записи не больше чем minIdleIntervalTimeout
				if (currentInterval > minIdleIntervalTimeout )
				{
					//добавляем запись о простое СП
					monitoringService.addOrUpdateServerIdleTime(new ServerIdleTime(getAppServerName(), serverLastPingDate, currentTime, IdleType.fullIdle));
				}

				serverWorkTime.setEndPingDate(currentTime);
			}

			//увеличиваем время последней активности
			serverWorkTime.setEndDate(currentTime);
			monitoringService.addOrUpdateServerCommonWorkTime(serverWorkTime);
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * В методе делается пинг тестовой страницы приложения.
	 * @param pingTimeoutMillis - таймаут пинга страницы мониторинга
	 * @param pageURL - адрес пингуемой страницы
	 * @return true пинг тестовой страницы был удачным
	 */
	private boolean pingPage(int pingTimeoutMillis, String pageURL) throws JobExecutionException
	{
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), pingTimeoutMillis);
		HttpConnectionParams.setSoTimeout(client.getParams(), pingTimeoutMillis);
		HttpGet httpGet = new HttpGet(pageURL);
		int status = 0;
		try
		{
			HttpResponse response = client.execute(httpGet);
			status = response.getStatusLine().getStatusCode();
		}
		catch (IOException e)
		{
			// ничего не делаем 
		}
		finally {
			httpGet.abort();
		}

		if (status == RESPONSE_CODE_OK)
			return true;
		else
			return false;
	}

	private String getNewPingPage()
	{
		try
		{
			MonitoringConfig monitoringConfig = ConfigFactory.getConfig(MonitoringConfig.class);
			BuildContextConfig buildContextConfig = ConfigFactory.getConfig(BuildContextConfig.class);
			return String.format(monitoringConfig.getPageURL(),
					ApplicationConfig.getIt().getApplicationHost().getHostName(),
									 buildContextConfig.getApplicationPort());
		}
		catch (UnknownHostException e)
		{
			throw new RuntimeException("Ошибка при получении URL тестовой страницы мониторинга: не найден хост", e);
		}
	}

	private String getPingPage()
	{
		String localPingPage = pingPage;
		if (StringHelper.isEmpty(localPingPage))
		{
			synchronized (PING_PAGE_LOCKER)
			{
				if (StringHelper.isEmpty(localPingPage))
					pingPage = getNewPingPage();
				localPingPage = pingPage;
			}
		}
		return localPingPage;
	}
}
