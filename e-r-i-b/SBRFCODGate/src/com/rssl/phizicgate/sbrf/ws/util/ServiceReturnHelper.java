package com.rssl.phizicgate.sbrf.ws.util;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.ClassHelper;

/**
 * Помошник для получения корректного сервиса.
 *
 * @author bogdanov
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class ServiceReturnHelper
{
	private static volatile ServiceReturnHelper instance;

	/**
	 * @param document документ, по которому определяем фабрику.
	 * @return фабрика гейта.
	 * @throws GateException
	 */
	public abstract GateFactory factoryFor(AbstractAccountTransfer document) throws GateException;

	/**
	 * @param document документ, для которого необходимо получить сервис.
	 * @param serviceClass получаемый сервис.
	 * @return сервис.
	 */
	public <S extends Service> S service(AbstractAccountTransfer document, Class<S> serviceClass) throws GateException
	{
		return factoryFor(document).service(serviceClass);
	}

	/**
	 * @param routeInfoReturner объект, содержаший информацию о маршрутизации.
	 * @param serviceClass получаемый сервис.
	 * @return сервис.
	 */
	public abstract <S extends Service> S service(Object routeInfoReturner, Class<S> serviceClass) throws GateException;

	/**
	 * @param factory фабрика гейта.
	 * @return url для отправки сообщений на шлюз.
	 */
	public abstract String getEndURL(GateFactory factory) throws GateException;

	public static ServiceReturnHelper getInstance() throws GateException
	{
		if (instance != null)
			return instance;

		synchronized (ServiceReturnHelper.class)
		{
			if (instance != null)
				return instance;

			try
			{
				instance = (ServiceReturnHelper)ClassHelper.loadClass(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(ServiceReturnHelper.class.getName())).newInstance();
				return instance;
			}
			catch (InstantiationException e)
			{
				throw new GateException(e);
			}
			catch (IllegalAccessException e)
			{
				throw new GateException(e);
			}
			catch (ClassNotFoundException e)
			{
				throw new GateException(e);
			}
		}
	}
}
