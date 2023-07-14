package com.rssl.phizic.business.dictionaries.config;

import com.rssl.phizic.config.PropertyReader;

import java.util.Map;
import java.util.Properties;

/**
 * @author Kidyaev
 * @ created 22.12.2006
 * @ $Author$
 * @ $Revision$
 */
public class SimpleDictionaryPathConfig extends DictionaryPathConfig
{
	private static final String PREFIX = "com.rssl.iccs.dictionaries.";
	private static final String SUFFIX = ".path";

	private Properties          dictionaryPathes = new Properties();

	/**
	 * ctor
	 * @param propertyReader читатель свойств :)
	 */
	public SimpleDictionaryPathConfig( PropertyReader propertyReader )
	{
	    super(propertyReader);
	}

	public void doRefresh()
	{
		try
		{			
			Properties properties = getProperties(PREFIX);

			for ( Map.Entry entry : properties.entrySet() )
			{
				String key = (String) entry.getKey();

				if ( !key.endsWith(SUFFIX) )
					continue;

				int    beginIndex     = PREFIX.length();
				int    endIndex       = key.length() - SUFFIX.length();
				String dictionaryName = key.substring(beginIndex, endIndex);

				dictionaryPathes.setProperty(dictionaryName, (String) entry.getValue());
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getPath(String dictionaryName)
	{
		return dictionaryPathes.getProperty(dictionaryName);
	}
}
