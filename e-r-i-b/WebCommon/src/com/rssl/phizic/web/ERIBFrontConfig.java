package com.rssl.phizic.web;

import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.annotation.Singleton;
import com.rssl.phizic.config.*;

/**
 * @author Erkin
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Конфиг для фронтальной (интерактивной) части ЕРИБ (WebClient, WebAdmin)
 */
@Singleton
public class ERIBFrontConfig extends Config
{
	private static final String UEC_WEBSITE_URL_PROPERTY_NAME = "com.rssl.erib.front.uec.url";

	private String uecWebSiteURL;
	///////////////////////////////////////////////////////////////////////////

	public ERIBFrontConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		uecWebSiteURL = getProperty(UEC_WEBSITE_URL_PROPERTY_NAME);
	}

	/**
	 * Возвращает адрес УЭК
	 * @return адрес сайта УЭК
	 */
	public String getUECWebSiteUrl()
	{
		return uecWebSiteURL;
	}
}
