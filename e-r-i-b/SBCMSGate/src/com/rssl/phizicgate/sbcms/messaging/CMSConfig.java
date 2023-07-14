package com.rssl.phizicgate.sbcms.messaging;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CMSConfig
{
	private static final String MOCK_KEY = "cms.mock";
	// URL webservice ож
	public static final String URL_WS_CMS_KEY = "cms.webservice.url";
	
	private static final Properties properties = readProperties();

	private static Properties readProperties()
	{
		Properties temp = new Properties(System.getProperties());
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("cms.properties");

		try
		{
			temp.load(stream);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return temp;
	}

	public static boolean IsMock()
	{
		return Boolean.parseBoolean(properties.getProperty(MOCK_KEY));
	}

	public static String getProperty(String key)
	{
		return properties.getProperty(key);
	}
}
