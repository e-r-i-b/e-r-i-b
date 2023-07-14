package com.rssl.phizicgate.stoplist.configs;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author usachev
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 * Конфиг для настроек стоп-листа
 */
public class StopListConfig extends Config
{

	private static final String STOP_LIST_SERVER_URL = "stop.list.servers.url";
	private static final String TIMEOUT = "stop.list.timeout";
	private static final String STOP_LIST_SYSTEM_CODE = "stop.list.system.code";

	private String stopListServerURL;
	private long timeout;
	private String stopListSystemCode;

	/**
	 * Конструктор по умолчанию
	 * @param reader Источник, из которого нужно читать данные
	 */
	public StopListConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		stopListServerURL = getProperty(STOP_LIST_SERVER_URL);
		timeout = getLongProperty(TIMEOUT);
		stopListSystemCode = getProperty(STOP_LIST_SYSTEM_CODE);
	}

	/**
	 * Получение ссылки на сервер-обработчик, занимающийся проверкой по стоп-листу.
	 * @return Ссылка на сервер-обработчик
	 */
	public String getStopListServerURL()
	{
		return stopListServerURL;
	}

	/**
	 * Получение времени ожидания запроса к внешней системе
	 * @return Время ожидания
	 */
	public long getTimeout()
	{
		return timeout;
	}

	/**
	 * Получение кода внешней системы "Стоп-лист"
	 * @return Код внешней системы "Стоп-лист"
	 */
	public String getStopListSystemCode()
	{
		return stopListSystemCode;
	}
}
