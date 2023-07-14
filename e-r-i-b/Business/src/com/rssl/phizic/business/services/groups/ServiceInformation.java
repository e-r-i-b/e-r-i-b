package com.rssl.phizic.business.services.groups;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о сервисе
 */

public class ServiceInformation
{
	private String key;
	private ServiceMode mode;

	/**
	 * @return ключ сервиса
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * задать ключ сервиса
	 * @param key ключ
	 */
	public void setKey(String key)
	{
		this.key = key;
	}

	/**
	 * @return режим сервиса
	 */
	public ServiceMode getMode()
	{
		return mode;
	}

	/**
	 * задать режим сервиса
	 * @param mode режим
	 */
	public void setMode(ServiceMode mode)
	{
		this.mode = mode;
	}
}
