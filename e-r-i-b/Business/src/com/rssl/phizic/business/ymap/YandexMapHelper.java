package com.rssl.phizic.business.ymap;

import com.rssl.phizic.config.ConfigFactory;

/**
 * @author Gulov
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 */
public class YandexMapHelper
{
	public static String getComponentUrl()
	{
		YandexMapConfig config = ConfigFactory.getConfig(YandexMapConfig.class);
		return config.getComponentUrl();
	}

	public static String getServiceUrl()
	{
		YandexMapConfig config = ConfigFactory.getConfig(YandexMapConfig.class);
		return config.getServiceUrl();
	}

	public static String getServiceTimeout()
	{
		YandexMapConfig config = ConfigFactory.getConfig(YandexMapConfig.class);
		return config.getServiceTimeout();
	}
}
