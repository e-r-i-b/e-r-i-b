package com.rssl.phizic.business.exception.locale;

import com.rssl.phizic.utils.test.SafeTaskBase;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author akrenev
 * @ created 01.11.2014
 * @ $Author$
 * @ $Revision$
 *
 * Загрузчик локалезависимых маппируемых сообщений
 */

public class LocalizingExceptionMappingLoader extends SafeTaskBase
{
	private static final LocalizingExceptionMappingService service = new LocalizingExceptionMappingService();
	private String file;

	/**
	 * @return имя файла
	 */
	public String getFile()
	{
		return file;
	}

	/**
	 * задать имя файла
	 * @param file имя файла
	 */
	public void setFile(String file)
	{
		this.file = file;
	}

	private String getValue(Properties properties, String key)
	{
		return (String) properties.get(key);
	}

	@Override
	public void safeExecute() throws Exception
	{
		Properties properties = new Properties();
		FileInputStream inputStream = new FileInputStream(getFile());

		try
		{
			properties.load(inputStream);
			int count = Integer.parseInt(getValue(properties, "exception.mapping.count"));
			for (int index = 0; index < count; index++)
			{
				String keyPrefix = "exception.mapping." + index + ".";
				LocalizingExceptionMapping mapping = new LocalizingExceptionMapping();
				mapping.setId((long) index);
				mapping.setMessageKey(getValue(properties, keyPrefix + "messageKey"));
				mapping.setErrorKey(getValue(properties, keyPrefix + "errorKey"));
				mapping.setPattern(getValue(properties, keyPrefix + "pattern"));
				mapping.setFormatter(getValue(properties, keyPrefix + "formatter"));
				service.save(mapping);
			}
			service.removeAll((long) count);
		}
		finally
		{
            inputStream.close();
		}
	}

	public LocalizingExceptionMappingLoader clone() throws CloneNotSupportedException
	{
		return (LocalizingExceptionMappingLoader) super.clone();
	}
}
