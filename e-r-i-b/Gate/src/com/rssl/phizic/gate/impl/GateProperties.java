package com.rssl.phizic.gate.impl;

import java.io.*;
import java.util.Properties;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * @author Kosyakov
 * @ created 19.10.2006
 * @ $Author: kosyakov $
 * @ $Revision: 2472 $
 */
public class GateProperties
{

	public static Properties getProperties () throws GateException
	{
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("gate.properties");
		if (stream==null)
		{
			throw new GateException("gate.properties not found");
		}
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
