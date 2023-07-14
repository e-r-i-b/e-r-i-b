package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.ConnectMode;
import com.rssl.phizic.gate.cache.CacheService;

/**
 * @author akrenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */
public abstract class GateConnectionConfig extends Config
{
	public static final String URL = "com.rssl.gate.connection.url";
	public static final String HOST = "com.rssl.gate.connection.host";
	public static final String PORT = "com.rssl.gate.connection.port";
	public static final String USER_NAME = "com.rssl.gate.connection.user.name";
	public static final String PASSWORD = "com.rssl.gate.connection.user.password";
	public static final String CONNECTION_TIMEOUT = "com.rssl.gate.connection.timeout";
	public static final String CONNECTION_BACK_TIMEOUT = "com.rssl.gate.connection.back.timeout";
	public static final String CONNECT_MODE = "com.rssl.gate.connection.mode";
	public static final String ESB_KEY = CacheService.class.getName()+".esb";
	public static final String ROUTABLE_KEY = CacheService.class.getName()+".routable";
	public static final String INTERNAL_WS_ADDRESS_KEY_PREFIX = "com.rssl.gate.internalws.address.";

	public GateConnectionConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return  URL для соединения с внешней системой
	 */
	public abstract String getURL();

	/**
	 * @return хост, на который устанавдивается соединение с внешней системой
	 */
	public abstract String getHost();

	/**
	 * @return порт, на который устанавдивается соединение с внешней системой
	 */
	public abstract String getPort();

	/**
	 * @return имя пользователя, от имени которого устанавливается соединение с внешней системой
	 */
	public abstract String getUserName();

	/**
	 * @return пароль пользователя, от имени которого устанавливается соединение с внешней системой
	 */
	public abstract String getPassword();

	/**
	 * @return время ожидания ответа от внешней системы в миллисекундах
	 */
	public abstract Long getConnectionTimeout();

	/**
	 * @return время ожидания ответа от основного приложения в миллисекундах
	 */
	public abstract Long getConnectionBackTimeout();

	/**
	 * @return режим работы с внешней системой: асинхронный или синхронный
	 */
	public abstract ConnectMode getConnectMode();

	/**
	 * @return реализация шины.
	 */
	public abstract Class getEsbClass();

	/**
	 * @return роутирующая реализация.
	 */
	public abstract Class getRoutableClass();

	/**
	 * @param destinationSystemName - название приложения
	 * @return Адрес веб-сервиса InternalServicePort приложения destinationSystemName
	 */
	public abstract String getInternalSystemWSAddress(String destinationSystemName);
}
