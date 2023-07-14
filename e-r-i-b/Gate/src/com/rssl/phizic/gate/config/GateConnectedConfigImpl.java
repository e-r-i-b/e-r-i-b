package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.ConnectMode;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author akrenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class GateConnectedConfigImpl extends GateConnectionConfig
{
	private Class esbService;
	private Class routableService;

	public GateConnectedConfigImpl(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		try
		{
			String esbClassName = getProperty(ESB_KEY);
			if (!StringHelper.isEmpty(esbClassName))
				esbService = ClassHelper.loadClass(esbClassName);

			String routableClassName = getProperty(ROUTABLE_KEY);
			if (!StringHelper.isEmpty(routableClassName))
				routableService = ClassHelper.loadClass(routableClassName);
		}
		catch (ClassNotFoundException e)
		{
			throw new ConfigurationException("Ошибка во время инициализации GateConnectedConfig", e);
		}
	}

	public String getURL()
	{
		return getProperty(URL);
	}

	public String getHost()
	{
		return getProperty(HOST);
	}

	public String getPort()
	{
		return getProperty(PORT);
	}

	public String getUserName()
	{
		return getProperty(USER_NAME);
	}

	public String getPassword()
	{
		return getProperty(PASSWORD);
	}

	public Long getConnectionTimeout()
	{
		return getLongProperty(CONNECTION_TIMEOUT);
	}

	public Long getConnectionBackTimeout()
	{
		return getLongProperty(CONNECTION_BACK_TIMEOUT);
	}

	public ConnectMode getConnectMode()
	{
		return getEnumProperty(ConnectMode.class, CONNECT_MODE);
	}

	@Override
	public Class getEsbClass()
	{
		return esbService;
	}

	@Override
	public Class getRoutableClass()
	{
		return routableService;
	}

	/**
	 * @param destinationSystemName - название приложения
	 * @return Адрес веб-сервиса InternalServicePort приложения destinationSystemName
	 */
	public String getInternalSystemWSAddress(String destinationSystemName)
	{
		return getProperty(INTERNAL_WS_ADDRESS_KEY_PREFIX + destinationSystemName);
	}

	protected Integer getIntegerProperty(String key)
	{
		String value = getProperty(key);
		if (value == null)
			return null;
		return Integer.valueOf(value);
	}

	protected <E extends Enum<E>> E getEnumProperty(Class<E> enumCalss, String key)
	{
		String value = getProperty(key);
		return StringHelper.isNotEmpty(value) ? Enum.valueOf(enumCalss, value) : null;
	}
}