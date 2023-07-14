package com.rssl.phizic.util;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;

/**
 * Утилитный класс для WebAPI
 * @author Jatsky
 * @ created 25.07.14
 * @ $Author$
 * @ $Revision$
 */

public class WebAPIUtil
{
	public static String getWebAPIUrl(String operation)
	{
		WebAPIConfig webAPIConfig = ConfigFactory.getConfig(WebAPIConfig.class);
		return webAPIConfig.getWebAPIUrl(operation);
	}
}
