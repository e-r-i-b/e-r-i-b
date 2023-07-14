package com.rssl.phizic.config;

/**
 * объекты умеющие возвращать значения настроек.
 *
 * @author bogdanov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */

public interface PropertyGetter
{
	/**
	 * @param key имя свойства
	 * @return значение свойства
	 */
	public String getProperty(String key);
}
