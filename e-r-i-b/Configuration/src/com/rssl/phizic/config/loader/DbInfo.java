package com.rssl.phizic.config.loader;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.config.ApplicationConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Информация о дб-ридере.
 *
 * @author bogdanov
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class DbInfo
{
	/**
	 * Название ридера.
	 */
	private String name;
	/**
	 * Интсанс приложения на инстанс базы.
	 */
	private Map<String, String> applicationInstanceToDbInstance = new HashMap<String, String>();
	/**
	 * Период обновления.
	 */
	private int periodForUpdate;

	public String getDbInstance(String appInstance)
	{
		return applicationInstanceToDbInstance.get(appInstance);
	}

	public void setDbInstance(String dbInstance, String appInstance)
	{
		applicationInstanceToDbInstance.put(appInstance, dbInstance);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPeriodForUpdate()
	{
		return periodForUpdate;
	}

	public void setPeriodForUpdate(int periodForUpdate)
	{
		this.periodForUpdate = periodForUpdate;
	}
}
