package com.rssl.phizic.logging;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.*;
import com.rssl.phizic.config.build.BuildContextConfig;
import com.rssl.phizic.logging.extendedLogging.ClientExtendedLoggingService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

/**
 * Хелпер для логирования
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoggingHelper extends Config
{
	private static final String APP_SERVER_INFO_MSG = "%s;%s;%s;";
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private boolean extendedLoggingOn;


	public LoggingHelper(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * используется ли в приложении расширенное логирование
	 */
	public boolean isExtendedLoggingOn()
	{
		return extendedLoggingOn;
	}

	/**
	 * Возвращает режим логирования
	 * @param accessor - акцессор для вычисления доступа к логированию
	 * @param parameters - параметры логирования
	 * @return режим логирования
	 */
	public LoggingMode getLoggingMode(LoggingAccessor accessor, Object... parameters)
	{
		if (accessor.needNormalModeLogging(parameters))
			return LoggingMode.NORMAL;

		if (!isExtendedLoggingOn())
			return LoggingMode.OFF;

		Long loginId = LogThreadContext.getLoginId();
		if (loginId != null && Application.PhizIA != LogThreadContext.getApplication())
		{
			if (!accessor.needExtendedModeLogging(parameters))
				return LoggingMode.OFF;
			else
			{
				//проверяем включение расширенного логирования у клиента
				if (ClientExtendedLoggingService.getInstance().getValidEntry(loginId) == null)
					return LoggingMode.OFF;
				else
					return LoggingMode.EXTENDED;
			}
		}

		return LoggingMode.OFF;
	}

	public int getLastDays(String prefix)
	{
		return Integer.parseInt(getProperty(prefix + Constants.AUTO_ARCHIVE_LEAVING_SUFFIX));
	}

	public boolean isEnabled(String prefix)
	{
		return "true".equalsIgnoreCase(getProperty(prefix + Constants.AUTO_ARCHIVE_SUFFIX));
	}

	public String getPath(String prefix)
	{
		return FileHelper.normalizePath(getProperty(prefix + Constants.AUTO_ARCHIVE_FOLDER_SUFFIX));
	}

	/**
	 * Сохранение в контексте логирования информации: имя, порт сервера и IP DataPower в формате: AAA…AAA;BBBB;CCC…CCC;
	 * @param request
	 */
	public static void setAppServerInfoToLogThreadContext(HttpServletRequest request)
	{
		try
		{
			String appServerName = null;
			String appServerPort = null;

			InetAddress localHost = ApplicationConfig.getIt().getApplicationHost();

			if (localHost != null)
				appServerName = localHost.getHostName();

			BuildContextConfig buildContextConfig = ConfigFactory.getConfig(BuildContextConfig.class);
			if (buildContextConfig != null)
				appServerPort = buildContextConfig.getApplicationPort();

			String ipAddress = request == null ? LogThreadContext.getIPAddress() : request.getRemoteAddr();
			String appServerInfo = String.format(APP_SERVER_INFO_MSG, appServerName, appServerPort, StringHelper.getEmptyIfNull(ipAddress));

			LogThreadContext.setAppServerInfo(appServerInfo);
		}
		catch (UnknownHostException e)
		{
			log.error("Не удалось определить host сервера", e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при сохранении информации в контексте логирования.", e);
		}
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		extendedLoggingOn = getBoolProperty("com.rssl.phizic.logging.extendedLogging.on");
	}
}
