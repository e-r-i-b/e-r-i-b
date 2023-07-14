package com.rssl.phizic.web.util;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.actions.UserAgentActionMapping;
import org.apache.struts.action.ActionMapping;

/**
 * Утилитный класс для mAPI. Только для веб-уровня.
 * @author Rydvanskiy
 * @ created 21.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class MobileApiWebUtil
{
	private static final String USER_AGENT_HEADER = "User-Agent";

	/**
	 * Является ли запрос, запросом с мобильного устройства используя API
	 * Deprecated! Использовать com.rssl.phizic.util.ApplicationUtil#isMobileApi()
	 * Оставлено только для поддержки 1.04. 
	 * @param mapping текущий ActionMapping
	 * @return Является ли запрос, запросом с использованием API
	 */
	@Deprecated
	public static boolean isMobileApiRq(ActionMapping mapping)
	{
		// key-feature: ЕРИБ API
		// A. Если ЕРИБ API v2.x, то имеем запрос с мобильного устройства однозначно
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		if (applicationConfig.getApplicationInfo().isMobileApi())
			return true;

		// B. Если указали правильный USER-AGENT, то считаем что тоже запрос с мобильного устройства
		if (mapping instanceof UserAgentActionMapping)
		{
			UserAgentActionMapping userAgentActionMapping = (UserAgentActionMapping) mapping;
			if (UserAgent.mobile.getName().equals(userAgentActionMapping.getUserAgent()))
				return true;
		}
		
		return false;
	}
}
