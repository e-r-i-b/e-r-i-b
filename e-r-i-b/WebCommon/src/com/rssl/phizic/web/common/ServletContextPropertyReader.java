package com.rssl.phizic.web.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;

/**
 * @author Evgrafov
 * @ created 24.07.2007
 * @ $Author: gladishev $
 * @ $Revision: 57750 $
 */

public class ServletContextPropertyReader
{
	private Properties properties;

	/**
	 * ѕрочитать ресурс из ServletContext
	 * @param context контекст
	 * @param resource им€ ресурса
	 * @throws IOException
	 */
	public ServletContextPropertyReader(ServletContext context, String resource) throws IOException
	{
		InputStream is = context.getResourceAsStream(resource);
		if(is == null)
			throw new IOException("Resource not found: " + resource);

		Properties temp = new Properties();
		temp.load(is);
		properties = temp;
	}

	public String getProperty(String key)
	{
		return properties.getProperty(key);
	}

	public Properties getProperties(String prefix)
	{
		Properties result = new Properties();

		for (Map.Entry entry : properties.entrySet())
		{
			String key = (String) entry.getKey();

			if (!key.startsWith(prefix))
				continue;

			result.setProperty(key, (String) entry.getValue());
		}

		return result;
	}
}