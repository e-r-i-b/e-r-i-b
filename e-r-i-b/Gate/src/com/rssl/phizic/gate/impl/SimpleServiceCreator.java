package com.rssl.phizic.gate.impl;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Omeliyanchuk
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class SimpleServiceCreator implements ServiceCreator
{
	public Service createService(String serviceClassName, GateFactory factory) throws GateException
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Class<?> serviceClass = classLoader.loadClass(serviceClassName);
			Service service = (Service) serviceClass.getConstructor(GateFactory.class).newInstance(factory);

			return service;
		}
		catch (Exception e)
		{
			throw new GateException("Ќе удалость зарегистрировать сервис " + serviceClassName, e);
		}		
	}
}
