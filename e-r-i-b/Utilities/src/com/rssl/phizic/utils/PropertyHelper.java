package com.rssl.phizic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 04.07.2006
 * @ $Author: gladishev $
 * @ $Revision: 57750 $
 */

public class PropertyHelper
{
	/**
	 * Существует ли ресурс с properties
	 * @param resource проперти файл
	 * @return true = существует
	 */
	public static boolean propertiesExists(String resource)
	{
		return getStream(resource) != null;
	}

	/**
	 * Прочитать property файл
	 * @param resource имя ресурса
	 * @return properties
	 */
	public static Properties readProperties(String resource)
	{
		InputStream resourceConf = getStream(resource);

		if (resourceConf == null)
			throw new RuntimeException(resource + " not found");

		try
		{
			return readPropertiesByStream(resourceConf);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed read resource " + resource, e);
		}
	}

	private static Properties readPropertiesByStream(InputStream resourceConf) throws IOException
	{
		Properties properties = new Properties();

		properties.load(resourceConf);

		for(Enumeration<?> enumeration = properties.propertyNames(); enumeration.hasMoreElements();)
		{
			String key = (String) enumeration.nextElement();
			String value = System.getProperty(key);
			if (value != null)
				properties.setProperty(key, value);
		}

		return properties;
	}

	private static InputStream getStream(String resource)
	{
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
	}

	public static Properties clone(Properties properties)
	{
		Properties result = new Properties();
		for(Enumeration<?> enumeration = properties.propertyNames(); enumeration.hasMoreElements(); )
		{
			String key = (String) enumeration.nextElement();
			result.setProperty(key, properties.getProperty(key));
		}

		return result;
	}

	/**
	 * Метод получает из формы список индексов табличной настройки с ключем key
	 * @param fields поля формы
	 * @param key - ключ
	 * @return список индексов
	 */
	public static List<Integer> getTableSettingNumbers(Map<String,Object> fields, String key)
	{
		List<Integer> result = new ArrayList<Integer>();
		int keyLength = key.length();
		for (String fieldName : fields.keySet())
		{
			if (!fieldName.startsWith(key))
				continue;

			result.add(Integer.parseInt(fieldName.substring(keyLength, fieldName.length())));
		}
		Collections.sort(result);
		return result;
	}
}