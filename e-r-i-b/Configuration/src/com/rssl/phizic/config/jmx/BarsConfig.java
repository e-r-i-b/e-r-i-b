package com.rssl.phizic.config.jmx;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.StringHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author osminin
 * @ created 06.04.2011
 * @ $Author$
 * @ $Revision$
 * считать настройки для БАРСа
 */
public class BarsConfig extends Config implements BarsConfigMBean
{
	private static final String DEFAUL_FILE_NAME                = "bars.csv";              //csv cправочник вида: ТБ;Code;URL
	private static final String DELIMITER                       = ";";
	private	static final String TB_CODE_PROPERTY_PREFIX         = "tb.code.";
	private static final String TB_URL_PROPERTY_PREFIX          = "tb.url.";

	private final Properties properties = new Properties();

	public BarsConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		if (!isProhibited())
		{
			return;
		}
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new	FileReader(getProperty("com.rssl.gate.bars.routes.dictionary.path") + File.separator + DEFAUL_FILE_NAME));
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] parameters = line.split(DELIMITER);
				String tb = StringHelper.addLeadingZeros(parameters[0], 2);

				setBarsUrl(tb, parameters[2]);
				setBarsPartCode(tb, parameters[1]);
			}
		}
		catch (IOException e)
		{
			throw new ConfigurationException("Ошибка при чтении файла " + DEFAUL_FILE_NAME, e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					throw new ConfigurationException("Ошибка при чтении файла " + DEFAUL_FILE_NAME, e);
				}
			}
		}
	}

	public String getBarsUrl(String tb)
	{
		return properties.getProperty(TB_URL_PROPERTY_PREFIX + StringHelper.addLeadingZeros(tb, 2));
	}

	public String getBarsPartCode(String tb)
	{
		return properties.getProperty(TB_CODE_PROPERTY_PREFIX + StringHelper.addLeadingZeros(tb, 2));
	}

	private void setBarsUrl(String tb, String value)
	{
		properties.setProperty(TB_URL_PROPERTY_PREFIX +	StringHelper.addLeadingZeros(tb, 2), value);
	}

	private void setBarsPartCode(String tb, String value)
	{
		properties.setProperty(TB_CODE_PROPERTY_PREFIX + StringHelper.addLeadingZeros(tb, 2), value);
	}

	private boolean isProhibited()
	{
		String value = System.getProperty("load.mbeans");
		if (StringHelper.isEmpty( value ))
		{
			return true;
		}

		return !value.equalsIgnoreCase("false");
	}
}