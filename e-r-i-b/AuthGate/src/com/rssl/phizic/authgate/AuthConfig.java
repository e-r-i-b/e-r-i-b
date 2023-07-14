package com.rssl.phizic.authgate;

/**
 * @author Gainanov
 * @ created 25.08.2009
 * @ $Author$
 * @ $Revision$
 */
public interface AuthConfig
{
	public String getProperty(String key);

	/**
	 * @param key имя свойства
	 * @param defaultValue значение по уполчанию.
	 * @return значение свойства
	 */
	public String getProperty(String key, String defaultValue);
}
