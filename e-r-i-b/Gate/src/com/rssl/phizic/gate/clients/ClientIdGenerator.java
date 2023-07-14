package com.rssl.phizic.gate.clients;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;

/**
 * @author hudyakov
 * @ created 13.07.2009
 * @ $Author$
 * @ $Revision$
 */

public abstract class ClientIdGenerator
{
	private static final Object CLIENT_ID_GENERATOR_LOCKER = new Object();
	private static final String CLIENT_ID_GENERATOR_PARAMMETER_NAME = "com.rssl.iccs.ClientIdGenerator";
	private static volatile ClientIdGenerator instance = null;

	public static ClientIdGenerator getInstance() throws GateException
	{
		ClientIdGenerator localInstance = instance;
		if (localInstance == null)
		{
			synchronized (CLIENT_ID_GENERATOR_LOCKER)
			{
				if (instance == null)
				{
					PropertyReader reader = ConfigFactory.getReaderByFileName("iccs.properties");
					String generatorClassName = reader.getProperty(CLIENT_ID_GENERATOR_PARAMMETER_NAME);
					try
					{
						Class<ClientIdGenerator> generatorClass = ClassHelper.loadClass(generatorClassName);
						instance = generatorClass.newInstance();
					}
					catch (Exception e)
					{
						throw new GateException(e);
					}
				}
				localInstance = instance;
			}
		}
		return localInstance;
	}

	public abstract String generate(Office office) throws GateLogicException, GateException;
}
