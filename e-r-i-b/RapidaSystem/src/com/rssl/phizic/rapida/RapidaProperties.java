package com.rssl.phizic.rapida;

import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author hudyakov
 * @ created 25.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class RapidaProperties
{
	public static Properties getProperties() throws GateException
	{
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("rapida.properties");

		if (stream == null)
	        throw new GateException("файл rapida.properties не найден");

		try
		{
			Properties properties = new Properties(System.getProperties());
			properties.load(stream);
		    return properties;
		}
		catch (IOException e)
		{
			throw new GateException(e);
		}

	}
}
